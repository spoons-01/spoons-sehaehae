package com.spoons.sehaehae.member.controller;

import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.common.exception.member.MemberModifyException;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.common.util.EmailUtil;
import com.spoons.sehaehae.member.dto.*;
import com.spoons.sehaehae.member.service.AuthenticationService;
import com.spoons.sehaehae.member.service.CouponRepository;
import com.spoons.sehaehae.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
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

    public MemberController(MemberService memberService, AuthenticationService authenticationService, MessageSourceAccessor messageSourceAccessor, PasswordEncoder passwordEncoder, EmailUtil emailUtil, CouponRepository couponRepository) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
        this.messageSourceAccessor = messageSourceAccessor;
        this.passwordEncoder = passwordEncoder;
        this.emailUtil = emailUtil;
        this.couponRepository = couponRepository;
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

        // 1. MemberDTO에서 membershipName을 추출한다
        String membershipName = extractMembershipName(memberDTO);
        // 2. 현재 로그인한 회원의 memberNo를 얻는다
        int memberNo = memberDTO.getMemberNo();
        // 2-2. MEMBER_NO를 이용하여 쿠폰 개수를 얻어온다
        int couponCount = couponRepository.countCouponsByMemberNo(memberNo);
        // 3. 내 주문 목록을 불러온다
        List<MyOrderDTO> myOrders = memberService.findMyOrder(currentUsername);
        // 4. 내 포인트를 불러온다.
        int myPoint = memberService.findMyPoint(memberNo);
        // 5. 주문 상태별 개수를 저장할 맵 초기화
        // 5-2. 주문 상태 배열에 넣기
        String[] orderedOrderStatuses = {"결제완료", "수거완료", "세탁완료", "배송준비", "배송중", "구매확정"};
        List<MyOrderDTO> myOrderList = memberService.findMyOrder(currentUsername);
        // 5-3. 주문 상태별 개수를 저장할 맵 초기화
        Map<String, Integer> orderStatusCounts = new LinkedHashMap<>();  // 순서가 중요하므로 LinkedHashMap 사용
        // 5-4. 정렬된 주문 상태 배열을 기반으로 초기화
        for (String status : orderedOrderStatuses) {
            orderStatusCounts.put(status, 0);
        }
        // 5-5. 주문 목록을 순회하면서 주문 상태 개수 계산
        for (MyOrderDTO order : myOrderList) {
            String orderStatus = order.getOrderStatus();
            orderStatusCounts.put(orderStatus, orderStatusCounts.get(orderStatus) + 1);
        }

        /* 1 */
        model.addAttribute("membershipName", membershipName);
        /* 2 */
        model.addAttribute("couponCount", couponCount);
        /* 3 */
        model.addAttribute("myOrders", myOrders);
        /* 4 */
        model.addAttribute("myPoint", myPoint);
        /* 5 */
        model.addAttribute("orderStatusCounts", orderStatusCounts);
        return "/user/member/mysehae";
    }

    // MemberDTO에서 membershipName을 추출하는 메소드
    private String extractMembershipName(MemberDTO memberDTO) {
        if (memberDTO != null && memberDTO.getMemberLevelList() != null) {
            for (MemberLevelDTO memberLevelDTO : memberDTO.getMemberLevelList()) {
                if (memberLevelDTO.getMembership() != null) {
                    return memberLevelDTO.getMembership().getMembershipName();
                }
            }
        }
        return "N/A"; // 기본값
    }

    @GetMapping("/member/myCoupon")
    public String myCoupon(Model model, Principal principal) {
        // 현재 로그인한 사용자의 아이디 얻기
        String currentUserName = principal.getName();
        // 현재 사용자의 MemberDTO를 얻어온다
        MemberDTO memberDTO = memberService.findByMemberId(currentUserName);
        // 현재 로그인한 회원의 memberNo를 얻는다
        int memberNo = memberDTO.getMemberNo();

        // 내 쿠폰 목록을 불러온다
        List<MyCouponDTO> myCoupons = memberService.findMyCoupon(memberNo);
        model.addAttribute("myCoupons", myCoupons);
        return "/user/member/myCoupon";
    }


    @GetMapping("/member/myOrder")
    public void myOrder() {
    }
    @GetMapping("/member/update")
    public void update(@AuthenticationPrincipal MemberDTO loginMember) {
        if (StringUtils.isBlank(loginMember.getProfilePhoto())) {
            loginMember.setProfilePhoto("/images/smile.png");
        }
    }

    @PostMapping("/member/update")
    public String modifyMember(MemberDTO modifyMember, MultipartFile attachImage,
                               @AuthenticationPrincipal MemberDTO loginMember, RedirectAttributes rttr) throws MemberModifyException {

        modifyMember.setMemberNo(loginMember.getMemberNo());

        log.info("modifyMember request Member : {}", modifyMember);
        log.info("modifyMember attachImage request : {}", attachImage);

        String fileUploadDir = IMAGE_DIR + "/original";

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
                attachImage.transferTo(new File(IMAGE_DIR + File.separator + savedFileName));
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

//        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.modify"));

        return "redirect:/user/member/update";
    }



    /* ================================================================== */

    protected Authentication createNewAuthentication(String memberId) {

        UserDetails newPrincipal = authenticationService.loadUserByUsername(memberId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(), newPrincipal.getAuthorities());

        return newAuth;
    }


    /**/
    /* 핸드폰 번호 인증 */
//    @PostMapping("/member/phoneAuth")
//    @ResponseBody
//    public Boolean phoneAuth(@RequestParam("tel") String tel, HttpSession session) {
//
//        try { // 이미 가입된 전화번호가 있으면
//            if (memberService.memberTelCount(tel) > 0)
//                return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String code = memberService.sendRandomMessage(tel);
//        session.setAttribute("rand", code);
//
//        return false;
//    }


//    @PostMapping("/member/phoneAuthOk")
//    @ResponseBody
//    public Boolean phoneAuthOk(HttpSession session, HttpServletRequest request) {
//        String rand = (String) session.getAttribute("rand");
//        String code = request.getParameter("code");
//
//        System.out.println(rand + " : " + code);
//
//        if (rand.equals(code)) {
//            session.removeAttribute("rand");
//            return false;
//        }
//
//        return true;
//    }

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
                                    "<h1>인증 사용자 : 홍길동</h1>"
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


}
