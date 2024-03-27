package com.spoons.sehaehae.member.controller;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.admin.dto.RefundDTO;
import com.spoons.sehaehae.board.dto.AttachmentDTO;
import com.spoons.sehaehae.board.dto.ReplyDTO;
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
import com.spoons.sehaehae.product.dto.PointDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import oracle.ucp.proxy.annotation.Post;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
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

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    private final MessageSourceAccessor messageSourceAccessor;
    private final PasswordEncoder passwordEncoder;
    private EmailUtil emailUtil;
    private final CouponRepository couponRepository;
    private final BoardService boardService;

    public MemberController(MemberService memberService, AuthenticationService authenticationService,
                            MessageSourceAccessor messageSourceAccessor, PasswordEncoder passwordEncoder,
                            EmailUtil emailUtil, CouponRepository couponRepository,
                            BoardService boardService) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
        this.messageSourceAccessor = messageSourceAccessor;
        this.passwordEncoder = passwordEncoder;
        this.emailUtil = emailUtil;
        this.couponRepository = couponRepository;
        this.boardService = boardService;
    }

    @GetMapping("/main/main")
    public void mainPage() {
    }


    /* 로그인 */
    @GetMapping("/member/login")
    public void login() {
    }

    @PostMapping("/member/loginfail")
    public String loginFailed(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("error.login"));
        return "redirect:/user/member/login";
    }

    @GetMapping("/member/findMyId")
    public void findMyId() {
    }

    /* 회원가입 */
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

    /* 아이디 찾기 */
    @GetMapping("/member/searchMyId")
    public void searchMyIdPage() {
    }

    @GetMapping("/member/findMyIdResult")
    public void findMyIdResultPage() {
    }

    @PostMapping("/member/searchMyId")
    public String searchMyId(@RequestParam("phone") String phoneNumber, Model model) {
        String memberId = memberService.searchMyId(phoneNumber);

        if(memberId !=null) {
            model.addAttribute("memberId", memberId);
            return "/user/member/findMyIdResult";
        } else {
            model.addAttribute("error", "해당 번호로 등록된 아이디를 찾을 수 없습니다.");
            return "/user/member/findMyIdResult";
        }
    }

    /* 비밀번호 찾기 */
    @GetMapping("/member/pwdEmailCheck")
    public void pwdEmailCheckPage() {}

    @GetMapping("/member/pwdSuccess")
    public void pwdSuccessPage() {}

    // 가입된 회원인지 확인
    @PostMapping("/member/idDupCheckForPwd")
    public ResponseEntity<String> checkDuplicationForPwd(@RequestBody MemberDTO member) {
        log.info("Request Check ID : {}", member.getMemberId());
        String result = "가입된 아이디가 존재하지 않습니다. 아이디를 확인해주세요.";
        if (memberService.selectMemberById(member.getMemberId())) {
            result = "가입된 아이디가 존재합니다.";
        }
        return ResponseEntity.ok(result);
    }

    // 신규 비밀번호 생성 및 DB 업데이트, 이메일 전송
    @PostMapping("/member/updatePasswordAndSendEmail")
    public ResponseEntity<?> updatePasswordSendEmail(@RequestBody EmailDTO emailDTO) {
        try {
            String newTempPassword = emailUtil.sendTempPassword(emailDTO);
            memberService.updateMemberPassword(emailDTO.getEmail(), newTempPassword);

            log.info("업데이트 비밀번호 : {}", newTempPassword);

            return ResponseEntity.ok(Map.of("success", true, "message", "새로운 비밀번호가 이메일로 발송되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "비밀번호 업데이트 및 발송 실패 : " + e.getMessage()));
        }
    }


    /* 마이페이지 메인 */
    @GetMapping("/member/mypage")
    public String myPage(Model model, Principal principal) {
        String currentUsername = principal.getName();
        MemberDTO memberDTO = memberService.findByMemberId(currentUsername);
        int memberNo = memberDTO.getMemberNo();

        List<ReviewDTO> myReviews = memberService.findMyReview(memberNo);
        List<ReplyDTO> myReplys = memberService.findMyReply(memberNo);

        model.addAttribute("myReviews", myReviews);
        model.addAttribute("myReplys", myReplys);
        return "/user/member/mypage";
    }

    /* 나의 세해 */
    @GetMapping("/member/mysehae")
    public String mySehae(Model model, Principal principal) {
        String currentUsername = principal.getName();
        MemberDTO memberDTO = memberService.findByMemberId(currentUsername);
        String membershipName = extractMembershipName(memberDTO);

        int memberNo = memberDTO.getMemberNo();
        int couponCount = couponRepository.countCouponsByMemberNo(memberNo);
        List<MyOrderDTO> myOrders = memberService.findMyOrder(currentUsername);
        int myPoint = memberService.findMyPoint(memberNo);

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

    /* 주문 목록 조회 (기간별 및 상태별) */
    @GetMapping("/member/MyOrders")
    public ResponseEntity<List<MyOrderDTO>> handleSearch(
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchStatusCondition", required = false) String searchStatusCondition) {
        List<MyOrderDTO> myOrders = memberService.findByConditions(searchCondition, searchStatusCondition);
        return ResponseEntity.ok(myOrders);
    }

    /* 쿠폰 목록 조회 */
    @GetMapping("/member/myCoupon")
    public String myCoupon(Model model, Principal principal) {
        String currentUserName = principal.getName();
        MemberDTO memberDTO = memberService.findByMemberId(currentUserName);
        int memberNo = memberDTO.getMemberNo();

        List<MyCouponDTO> myCoupons = memberService.findMyCoupon(memberNo);
        model.addAttribute("myCoupons", myCoupons);
        return "/user/member/myCoupon";
    }

    /* 주문 상세 조회 */
    @GetMapping("/member/myOrder/{orderCode}")
    public String myOrder(@PathVariable String orderCode, Model model) {
        MyOrderDTO myOrder = memberService.findMyOrderDetails(orderCode);
        List<MyOrderProductDTO> myProduct = memberService.findMyProduct(orderCode);
        model.addAttribute("myOrders", myOrder);
        model.addAttribute("myProduct", myProduct);
        return "/user/member/myOrder";
    }

    /* 환불 요청 */
    @GetMapping("/member/refund/{orderCode}")
    public String myRefund(@PathVariable String orderCode, Model model) {
        MyRefundDTO refund = memberService.findMyRefund(orderCode);
        model.addAttribute("refund", refund);
        return "/user/member/refund";
    }

    @PostMapping("/member/refund/{orderCode}")
    public String processRefund(MyRefundDTO refund,
                                @PathVariable String orderCode,
                                Model model) {
        refund.setOrderCode(orderCode);
        memberService.saveRefund(refund);
        return "redirect:/user/member/mysehae";
    }

    @GetMapping("/member/update")
    public void update(@AuthenticationPrincipal MemberDTO loginMember) {
        if (StringUtils.isBlank(loginMember.getProfilePhoto())) {
            loginMember.setProfilePhoto("/images/smile.png");
        }
    }

    /* 마이페이지-설정 수정(회원정보 수정) */
    @PostMapping("/member/update")
    public String modifyMember(MemberDTO modifyMember, MultipartFile attachImage,
                               @AuthenticationPrincipal MemberDTO loginMember, RedirectAttributes rttr) throws MemberModifyException {

        modifyMember.setMemberNo(loginMember.getMemberNo());

        log.info("modifyMember request Member : {}", modifyMember);
        log.info("modifyMember attachImage request : {}", attachImage);

        String fileUploadDir = IMAGE_DIR + "/original";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            if (attachImage.getSize() > 0) {
                String originalFileName = attachImage.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID() + ext;
                attachImage.transferTo(new File(IMAGE_DIR + File.separator + savedFileName));
                modifyMember.setProfilePhoto("/upload/" + savedFileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        memberService.modifyMember(modifyMember);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(loginMember.getMemberId()));
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.modify"));

        return "redirect:/user/member/update";
    }


    /* 이메일 인증 */
    protected Authentication createNewAuthentication(String memberId) {

        UserDetails newPrincipal = authenticationService.loadUserByUsername(memberId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(), newPrincipal.getAuthorities());

        return newAuth;
    }

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

        String fileUploadDir = IMAGE_DIR + "original";
        String thumbnailDir = IMAGE_DIR + "thumbnail";

        File dir1 = new File(fileUploadDir);
        File dir2 = new File(thumbnailDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if (!dir1.exists()) {
            dir1.mkdirs();
            dir2.mkdirs();
        }

        /* 업로드 파일에 대한 정보를 담을 리스트 */
        AttachmentDTO attachment = new AttachmentDTO();

        try {
            /* 첨부파일이 실제로 존재하는 경우에만 로직 수행 */
            if (attachImage.getSize() > 0) {

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
                Thumbnails.of(fileUploadDir + "/" + savedName).size(150, 150)
                        .toFile(thumbnailDir + "/thumbnail_" + savedName);
                attachment.setThumbnail("/upload/thumbnail/thumbnail_" + savedName);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("fileInfo : {}", attachment);

        review.setAttachment(attachment);
        review.setWriter(member);
        review.setMyOrders(myOrder);
        // 별점을 ReviewDTO에 설정
        review.setRating(rating);

        model.addAttribute("myOrders", myOrder);
        model.addAttribute("myPoint", myPoint);


        boardService.registReview(review, attachment, orderCode, point);

        return "redirect:/user/member/mysehae";
    }

}