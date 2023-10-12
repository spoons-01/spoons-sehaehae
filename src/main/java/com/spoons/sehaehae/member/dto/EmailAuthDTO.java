package com.spoons.sehaehae.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAuthDTO extends EmailDTO {

    // 이메일 인증값 검증 키
    @Builder.Default
    private String emailAuthKey = RandomStringUtils.randomAlphabetic(24);

    // 이메일 인증값 검증 값
    @JsonIgnore
    @Builder.Default
    private String emailAuthVal = RandomStringUtils.randomNumeric(6);

    // 이메일 인증값 -화면에서 인증값 검증
    private String emailAuthInVal;

    // 이메일 인증 결과 메시지 반환
    private String resultMsg;

    // 이메일 인증값 생성 시간
    @Builder.Default
    private Date regDt = new Date();

    // 이메일 인증값 만료 시간
    //VO 생성 시점부터 10분 이내 인증 해야됨.
    @Builder.Default
    private Date expireDt = DateUtils.addMinutes(new Date(), 10);

}