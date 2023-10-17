package com.spoons.sehaehae.member.controller;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.board.dto.AttachmentDTO;
import com.spoons.sehaehae.board.dto.ReviewPointDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.board.service.BoardService;
import com.spoons.sehaehae.common.exception.member.MemberModifyException;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.common.util.EmailUtil;
import com.spoons.sehaehae.member.dto.*;
import com.spoons.sehaehae.member.service.AuthenticationService;
import com.spoons.sehaehae.member.service.CouponRepository;
import com.spoons.sehaehae.member.service.MemberService;
import com.spoons.sehaehae.product.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/user")
public class MemberController {

    @Value("${image.image-dir}") // 코드상의 변경 없이 디렉토리 변경 가능(yml)
    private String IMAGE_DIR;
    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    private final MessageSourceAccessor messageSourceAccessor;
    private final PasswordEncoder passwordEncoder;
    private EmailUtil emailUtil;
    private final CouponRepository couponRepository;
    private final BoardService boardService; // 인선!!!!

    public MemberController(MemberService memberService, AuthenticationService authenticationService, MessageSourceAccessor messageSourceAccessor, PasswordEncoder passwordEncoder, EmailUtil emailUtil, CouponRepository couponRepository
                            ,BoardService boardService ) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
        this.messageSourceAccessor = messageSourceAccessor;
        this.passwordEncoder = passwordEncoder;
        this.emailUtil = emailUtil;
        this.couponRepository = couponRepository;
        this.boardService = boardService; // 인선!!!!!
    }

    @GetMapping("/main/main")
    public void mainPage() {
    }

    @GetMapping("member/login")
    public void login() {
    }

    @PostMapping("/member/loginfail")
    public String loginFailed(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("error.login"));
        return "redirect:/user/member/login";
    }

    @GetMapping("/member/regist")
    public void registPage() {
    }

    @PostMapping("/member/idDupCheck")
    public ResponseEntity<String> checkDuplication(@RequestBody MemberDTO member) {
        log.info("Request Check ID : {}", member.getMemberId());
        String result = "사용 가능한 아이디입니다.";
        if (memberService.selectMemberById(member.getMemberId())) {
            result = "중복된 아이디가 존재합니다.";
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/member/regist")
    public String registMember(MemberDTO member,
                               RedirectAttributes rttr) throws MemberRegistException {

        member.setMemberPwd(passwordEncoder.encode(member.getPassword()));
        log.info("Request regist member : {}", member);
        memberService.registMember(member);
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.regist"));

        return "redirect:/";
    }

    @GetMapping("/member/mypage")
    public String myPage(Model model, Principal principal) {
        String currentUsername = principal.getName();
        MemberDTO memberDTO = memberService.findByMemberId(currentUsername);
        int memberNo = memberDTO.getMemberNo();
        List<ReviewDTO> myReviews = memberService.findMyReview(memberNo);
        model.addAttribute("myReviews", myReviews);

        return "/user/member/mypage";
    }

    /* 나의 세해 이동 */
    @GetMapping("/member/mysehae")
    public String mySehae(Model model, Principal principal) {
        // 현재 로그인한 사용자의 아이디 얻기
        String currentUsername = principal.getName();
        // 현재 사용자의 MemberDTO를 얻어온다
        MemberDTO memberDTO = memberService.findByMemberId(currentUsername);

        // MemberDTO에서 membershipName을 추출한다
        String membershipName = extractMembershipName(memberDTO);

        int memberNo = memberDTO.getMemberNo();
        int couponCount = couponRepository.countCouponsByMemberNo(memberNo);
        List<MyOrderDTO> myOrders = memberService.findMyOrder(currentUsername);
        int myPoint = memberService.findMyPoint(memberNo);

        // 5. 주문 상태별 개수를 저장할 맵 초기화
        String[] orderedOrderStatuses = {"결제완료", "수거완료", "세탁완료", "배송준비", "배송중", "구매확정"};
        List<MyOrderDTO> myOrderList = memberService.findMyOrder(currentUsername);

        // 5-1. 주문 상태별 개수를 저장할 맵 초기화
        Map<String, Integer> orderStatusCounts = new LinkedHashMap<>();  // 순서가 중요하므로 LinkedHashMap 사용

        // 5-2. 정렬된 주문 상태 배열을 기반으로 초기화
        for (String status : orderedOrderStatuses) {
            orderStatusCounts.put(status, 0);
        }
        // 5-3. 주문 목록을 순회하면서 주문 상태 개수 계산
        for (MyOrderDTO order : myOrderList) {
            String orderStatus = order.getOrderStatus();
            orderStatusCounts.put(orderStatus, orderStatusCounts.get(orderStatus) + 1);
        }

        model.addAttribute("membershipName", membershipName);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("myOrders", myOrders);
        model.addAttribute("myPoint", myPoint);
        model.addAttribute("orderStatusCounts", orderStatusCounts);
        return "/user/member/mysehae";
    }

    private String extractMembershipName(MemberDTO memberDTO) {
        if (memberDTO != null && memberDTO.getMemberLevelList() != null) {
            for (MemberLevelDTO memberLevelDTO : memberDTO.getMemberLevelList()) {
                if (memberLevelDTO.getMembership() != null) {
                    return memberLevelDTO.getMembership().getMembershipName();
                }
            }
        }
        return "N/A";
    }

    @GetMapping("/member/myCoupon")
    public String myCoupon(Model model, Principal principal) {
        String currentUserName = principal.getName();
        MemberDTO memberDTO = memberService.findByMemberId(currentUserName);
        int memberNo = memberDTO.getMemberNo();

        List<MyCouponDTO> myCoupons = memberService.findMyCoupon(memberNo);
        model.addAttribute("myCoupons", myCoupons);
        return "/user/member/myCoupon";
    }

    @GetMapping("/member/myOrder/{orderCode}")
    public String myOrder(@PathVariable String orderCode, Model model) {
        MyOrderDTO orderDTO = memberService.findMyOrderDetails(orderCode);
        model.addAttribute("myOrders", orderDTO);
        return "/user/member/myOrder";
    }


    /* 마이페이지-설정 이동 */
    @GetMapping("/member/update")
    public void update() {
    }

    /* 마이페이지-설정 수정(회원정보 수정) */
    @PostMapping("/member/update")
    public String modifyMember(MemberDTO modifyMember, MultipartFile attachImage,
                               @AuthenticationPrincipal MemberDTO loginMember, RedirectAttributes rttr) throws MemberModifyException {

        modifyMember.setMemberNo(loginMember.getMemberNo());

        log.info("modifyMember request Member : {}", modifyMember);
        log.info("modifyMember attachImage request : {}", attachImage);

        String fileUploadDir = IMAGE_DIR + "original";

        File dir = new File(fileUploadDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 여러 개의 파일을 받을 게 아니라서, DTO와 리스트는 필요 없다.
        //List<ProfileAttachmentDTO> profileAttachmentList = new ArrayList<>();

        try {
            if (attachImage.getSize() > 0) {
                /* 첨부파일이 실제로 존재하는 경우에만 로직 수행 */

                String originalFileName = attachImage.getOriginalFilename();
                log.info("originalFileName : {}", originalFileName);

                String ext = originalFileName.substring(originalFileName.lastIndexOf(".")); // 원본 이름에서 확장자 추출
                String savedFileName = UUID.randomUUID() + ext; // 중복을 방지하기 위해 랜덤한 아이디 생성 후 확장자 삽입 후 새로운 이름에 할당
                log.info("savedFileName : {}", savedFileName);

                /* 서버의 설정 디렉토리에 파일 저장하기 */
                attachImage.transferTo(new File(fileUploadDir + "/" + savedFileName));
                modifyMember.setProfilePhoto("/upload/original/" + savedFileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        memberService.modifyMember(modifyMember);

        /* 로그인 시 저장 된 Authentication 객체를 변경 된 정보로 교체한다.
         * 수정 된 정보로 로그인 한 것과 동일한 효과를 얻을 수 있다. */
        SecurityContextHolder.getContext().
                setAuthentication(createNewAuthentication(loginMember.getMemberId()));

        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.modify"));

        return "redirect:/user/member/update";
    }



    protected Authentication createNewAuthentication(String memberId) {

        UserDetails newPrincipal = authenticationService.loadUserByUsername(memberId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(), newPrincipal.getAuthorities());

        return newAuth;
    }

    /**
     * 이메일 인증코드 발송
     *
     * @param session
     * @param emailAuthDTO
     * @return
     * @throws MessagingException
     */
    @PostMapping(value = "/member/regEmailAuth"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmailAuthDTO regEmailAuth(HttpSession session, @RequestBody EmailAuthDTO emailAuthDTO) throws MessagingException {
        log.info("emailDTO : {}", emailAuthDTO);

        log.info("인증 이메일 : {}", emailAuthDTO.getEmail());
        log.info("인증 세션 : {}", emailAuthDTO.getEmailAuthKey());
        log.info("인증 번호 : {}", emailAuthDTO.getEmailAuthVal());

        try {
            emailUtil.sndEmail(
                    EmailDTO.builder()
                            .email(emailAuthDTO.getEmail())
                            .content(
                                    "<h1>인증 사용자 : 세탁해병대</h1>"
                                            + "<h1>인증 요청 시간 : " + DateFormatUtils.format(emailAuthDTO.getRegDt(), "HH:mm:ss (E)") + "</h1>"
                                            + "<h1>인증 번호 : [" + emailAuthDTO.getEmailAuthVal() + "]</h1>"
                                            + "<h1>인증 만료 시간은 10분 입니다. 10분 이내에 입력 하여 주시기 바랍니다.</h1>"
                                            + "<h1>인증 만료 시간 : " + DateFormatUtils.format(emailAuthDTO.getExpireDt(), "HH:mm:ss (E)") + "</h1>"
                            )
                            .build()
            );
            session.setAttribute(emailAuthDTO.getEmailAuthKey(), emailAuthDTO);
        } catch (Exception e) {
            log.error("ERR -- {}", e);
            throw e;
        }
        return emailAuthDTO;
    }


    /*
     * JAva WEB 영역
     *
     * Request
     * Response
     * Session
     * Apllication
     * PageContext
     * */

    /**
     * 이메일 인증코드 인증
     *
     * @param session
     * @param emailAuthDTO
     * @return
     * @throws MessagingException
     */
    @PostMapping(value = "/member/chkEmailAuth"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmailAuthDTO chkEmailAuth(HttpSession session, @RequestBody EmailAuthDTO emailAuthDTO) throws MessagingException {
        log.info("emailDTO : {}", emailAuthDTO);

        EmailAuthDTO sessionEmailDTO = null;

        Date nowDate = new Date();
        try {
            sessionEmailDTO = (EmailAuthDTO) session.getAttribute(emailAuthDTO.getEmailAuthKey());

            // 1.인증 정보 체크(번호 유출방지)
            if (sessionEmailDTO == null) {
                emailAuthDTO.setResultMsg("인증 시도 정보가 존재하지 않습니다.");
                emailAuthDTO.setSuccess(false);
                return emailAuthDTO;
            }

            //2. 인증 만료 시간 (10분)
            // 현재 시간이 만료시간 보다 이후 인경우
            if (nowDate.getTime() >= sessionEmailDTO.getExpireDt().getTime()) {
                emailAuthDTO.setResultMsg("인증 시간이 만료되었습니다. 재인증 받으시기 바랍니다.");
                emailAuthDTO.setSuccess(false);
                return emailAuthDTO;
            }

            //3. 인증 값 체크
            // 세션 저장 값과 인증 요청 값이 동일 하지 않는 경우
            if (!StringUtils.equals(emailAuthDTO.getEmailAuthInVal(), sessionEmailDTO.getEmailAuthVal())) {
                emailAuthDTO.setResultMsg("인증 값이 틀렸습니다. 재 확인하시기 바랍니다.");
                emailAuthDTO.setSuccess(false);
                return emailAuthDTO;
            }

            emailAuthDTO.setSuccess(true);
            emailAuthDTO.setResultMsg("정상 처리 되었습니다.");
        } catch (Exception e) {
            log.error("ERR -- {}", e);
            throw e;
        }
        return emailAuthDTO;
    }


    /* 인선!!!! 후기작성 모달창 ---------------------------------------------------- */

    @PostMapping("/member/mysehae")
    public String registReview(ReviewDTO review, MultipartFile attachImage,
                               @AuthenticationPrincipal MemberDTO member,
                               @AuthenticationPrincipal OrderDTO myOrder,
                               @AuthenticationPrincipal ReviewPointDTO myPoint,
                               @RequestParam("rating") int rating,
                               Model model, Principal principal,
                               String orderCode,
                               ReviewPointDTO point) {

        log.info("review request : {}", review);
        log.info("attachImage request : {}", attachImage);

        // 별점을 ReviewDTO에 설정
        review.setRating(rating);

        String fileUploadDir = IMAGE_DIR + "original";
        String thumbnailDir = IMAGE_DIR + "thumbnail";

        File dir1 = new File(fileUploadDir);
        File dir2 = new File(thumbnailDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if(!dir1.exists() ) {
            dir1.mkdirs();
            dir2.mkdirs();
        }

        /* 업로드 파일에 대한 정보를 담을 리스트 */
        AttachmentDTO attachment = new AttachmentDTO();

        try {
                /* 첨부파일이 실제로 존재하는 경우에만 로직 수행 */
                    if(attachImage.getSize() > 0) {

                        String originalFileName = attachImage.getOriginalFilename();
                        log.info("originalFileName : {}", originalFileName);

                        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String savedName = UUID.randomUUID() + ext;
                        log.info("saveName : {}", savedName);

                        Long size = attachImage.getSize();

                        /* 서버의 설정 디렉토리에 파일 저장하기 */

                        attachImage.transferTo(new File(fileUploadDir + "/" + savedName));

                        /* DB에 저장할 파일의 정보 처리 */
                        AttachmentDTO fileInfo = new AttachmentDTO();
                        attachment.setName(originalFileName);
                        attachment.setSavedName(savedName);
                        attachment.setRoute("/upload/");
                        attachment.setExtension(ext);
                        attachment.setSize(size);
                        log.info("fileInfo : {}", attachment);
                        attachment.setEx("TITLE"); // 대표사진

                            /* 대표 사진에 대한 썸네일을 가공해서 저장한다. */
                            Thumbnails.of(fileUploadDir + "/" + savedName).size(150,150)
                                    .toFile(thumbnailDir + "/thumbnail_" + savedName);
                        attachment.setThumbnail("/upload/thumbnail/thumbnail_" + savedName);

                    }

            }catch (IOException e) {
        throw new RuntimeException(e);
        }

        log.info("fileInfo : {}", attachment);

        review.setAttachment(attachment);
        review.setWriter(member);
        review.setMyOrders(myOrder);

        model.addAttribute("myOrders", myOrder);
        model.addAttribute("myPoint", myPoint);


        boardService.registReview(review, attachment, orderCode, point);

        return "redirect:/user/member/mysehae";
        }

}
