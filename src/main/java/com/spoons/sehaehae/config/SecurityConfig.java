package com.spoons.sehaehae.config;

import com.spoons.sehaehae.member.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* 시큐리티 설정 활성화 및 bean 등록 가능 */
@EnableWebSecurity
public class SecurityConfig {

    /* url 오류 해결 */
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    private final AuthenticationService authenticationService;
    private final DomainFailureHandler domainFailureHandler;

    public SecurityConfig(AuthenticationService authenticationService, DomainFailureHandler domainFailureHandler) {
        this.authenticationService = authenticationService;
        this.domainFailureHandler = domainFailureHandler;
    }

    /* 비밀번호 암호화에 사용할 객체 BCryptPasswordEncoder bean 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /* Http 요청에 대한 설정을 SecurityFilterChain에 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                /* CSRF 공격 방지는 기본적으로 활성화 되어 있어 비활성화 처리 */
                .csrf()
                .disable()
                /* 요청에 대한 권한 체크 */
                .authorizeHttpRequests()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/board/**", "/thumbnail/**", "/user/member/update", "/user/member/delete").hasRole("MEMBER")

                /* 위에 서술 된 패턴 외의 요청은 인증 되지 않은 사용자도 요청 허가 */
                .anyRequest().permitAll()
                .and()
                /* 로그인 설정 */
                .formLogin()
                .loginPage("/user/member/login")
                .defaultSuccessUrl("/")
                //.failureForwardUrl("/user/member/loginfail")
                .failureHandler(domainFailureHandler)
                .usernameParameter("memberId")
                .passwordParameter("memberPwd")
                .and()
                /* 로그아웃 설정 */
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/member/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .and()
                /* 인증이 필요하면 로그인 페이지로 이동하므로 인가 처리만 설정  */
                .exceptionHandling()
                .accessDeniedPage("/error/denied")
                .and()
                .build();
    }

    /* 사용자 인증을 위해서 사용할 Service 등록 & 비밀번호 인코딩 방식 지정 */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {

        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(authenticationService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

}