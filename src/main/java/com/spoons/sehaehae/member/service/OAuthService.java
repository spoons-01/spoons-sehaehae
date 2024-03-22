package com.spoons.sehaehae.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.member.dto.KakaoInfo;
import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service @Slf4j @Transactional
public class OAuthService {

    private final MemberService memberService;

    public OAuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Value("${kakao.client.id}")
    String clientId;

    @Value("${kakao.redirect.uri}")
    String redirectUri;


    /* 1. 토큰 받기 : 카카오에서 전달해준 인가 코드로 토큰 받기 */
    public String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    /* 2. 액세스 토큰으로 사용자 정보 가져오기 */
    public KakaoInfo getKakaoInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보 꺼내기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        return new KakaoInfo(id, nickname, email);
    }

    /* 3. 카카오 사용자 정보 확인 */
    public MemberDTO ifNeedKakaoInfo(KakaoInfo kakaoInfo) throws MemberRegistException {
        // DB에 중복되는 email이 있는지 확인
        String kakaoEmail = kakaoInfo.getEmail();
        MemberDTO kakaoMember = memberService.findMemberEmail(kakaoEmail);

        // 회원가입
        if (kakaoMember == null) {
            String kakaoNickname = kakaoInfo.getNickname();
            // 이메일로 아이디 발급
            String kakaoId = kakaoEmail;
            // 임시 비밀번호 발급
            String tempPassword = UUID.randomUUID().toString();

            MemberDTO member = new MemberDTO();
            member.setMemberId(kakaoId);
            member.setMemberPwd(tempPassword);
            member.setNickname(kakaoNickname);

            // 나머지 필드에 기본값 설정 - 최초 로그인 시 수정
            member.setName("이름을 변경해주세요.");
            member.setPhone("01000000000");
            member.setBirthday("01-01-0001");
            member.setGender('M');
            member.setZipCode("00000");
            member.setAddress1("주소 검색을 통해 주소를 변경해주세요.");
            member.setAddress2("");

            memberService.registMember(member);

            // DB 재조회
            kakaoMember = memberService.findMemberEmail(kakaoEmail);
        }
        return kakaoMember;
    }

}
