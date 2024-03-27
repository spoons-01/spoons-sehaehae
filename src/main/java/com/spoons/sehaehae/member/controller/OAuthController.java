package com.spoons.sehaehae.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.member.dto.KakaoInfo;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.service.AuthenticationService;
import com.spoons.sehaehae.member.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {
    private final OAuthService oAuthService;
    private final AuthenticationService authenticationService;

    public OAuthController(OAuthService oAuthService, AuthenticationService authenticationService) {
        this.oAuthService = oAuthService;
        this.authenticationService = authenticationService;
    }

    @Value("${kakao.client.id}")
    String clientId;

    @Value("${kakao.redirect.uri}")
    String redirectUri;

    @GetMapping(value="/kakao")
    public String kakaoConnect() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+clientId);
        url.append("&redirect_uri="+redirectUri);
        url.append("&response_type=code");
        return "redirect:" + url.toString();
    }

    @GetMapping("/kakao/callback")
    public String kakaoCallback(String code, HttpSession session) throws MemberRegistException {
        // 1. 인가 코드 받기
        log.info("code : {}", code);

        // 2. 인가코드를 기반으로 토큰(Access Token) 발급
        String accessToken = null;
        try {
            accessToken = oAuthService.getAccessToken(code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("액세스 토큰 : {}", accessToken);

        // 3. 토큰을 통한 사용자 정보 조회
        KakaoInfo kakaoInfo = null;
        try {
            kakaoInfo = oAuthService.getKakaoInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("이메일 확인 : {}", kakaoInfo.getEmail());

        // 4. 카카오 사용자 정보 확인
        MemberDTO kakaoMember = oAuthService.ifNeedKakaoInfo(kakaoInfo);

        // 5. 강제 로그인
        if (kakaoMember != null) {
            // 5-1. UserDetails 객체 가져오기
            UserDetails userDetails = authenticationService.loadUserByUsername(kakaoMember.getUsername());

            // 5-2. Spring Security 인증 객체 생성
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // 5-3. SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 5-4. 세션에 추가 정보 저장
            session.setAttribute("loginMember", kakaoMember);
            session.setMaxInactiveInterval(60 * 30); // 세션 유지 시간 설정
            session.setAttribute("kakaoToken", accessToken);

        }

        return "redirect:/";
    }
}
