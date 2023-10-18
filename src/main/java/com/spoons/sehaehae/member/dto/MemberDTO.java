package com.spoons.sehaehae.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Getter @Setter @ToString
public class MemberDTO implements UserDetails {
    private int memberNo;
    private String memberId;
    private String memberPwd;
    private String name;
    private String nickname;
    private String phone;
    private String birthday;
    private char gender;
    private String zipCode;
    private Date subscriptionDate;
    private String profilePhoto;
    private String referralCode;
    private String memberStatus;

    private String address1;
    private String address2;

    private List<MemberRoleDTO> memberRoleList;
    private List<MemberLevelDTO> memberLevelList;

    @JsonIgnore
    @Override
    /* Authority에 대해서 정해진 형태로 반환해야 함 */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (MemberRoleDTO role : memberRoleList) {
            roles.add(new SimpleGrantedAuthority(role.getAuthority().getName()));
        }
        return roles;
    }


    @Override
    public String getPassword() {
        return memberPwd;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    /* 계정 만료 여부 */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* 계정 잠금 여부 */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* 계정 비밀번호 만료 여부 */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* 계정 활성화 여부 */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
