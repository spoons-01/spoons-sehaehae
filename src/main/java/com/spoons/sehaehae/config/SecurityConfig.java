package com.spoons.sehaehae.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* 시큐리티 설정 활성화 및 bean 등록 가능 */
@EnableWebSecurity
public class SecurityConfig {    /* 비밀번호 암호화에 사용할 객체 BCryptPasswordEncoder bean 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /* Http 요청에 대한 설정을 SecurityFilterChain에 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                /* CSRF 공격 방지는 기본적으로 활성화 되어 있어 비활성화 처리 */
                .csrf()
                .disable()
             //   /* 요청에 대한 권한 체크 */
             //   .authorizeHttpRequests()
             //   .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
             //   .antMatchers("/board/**", "/thumbnail/**", "/member/update", "/member/delete").hasRole("MEMBER")
             //   /* 위에 서술 된 패턴 외의 요청은 인증 되지 않은 사용자도 요청 허가 */
             //   .anyRequest().permitAll()
             //   .and()
             //   /* 로그인 설정 */
             //   .formLogin()
             //   .loginPage("/member/login")
             //   .defaultSuccessUrl("/")
             //   .failureForwardUrl("/member/loginfail")
             //   .usernameParameter("memberId")
             //   .passwordParameter("memberPwd")
             //   .and()
             //   /* 로그아웃 설정 */
             //   .logout()
             //   .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
             //   .deleteCookies("JSESSIONID")
             //   .invalidateHttpSession(true)
             //   .logoutSuccessUrl("/")
             //   .and()
                .build();
    }




}
