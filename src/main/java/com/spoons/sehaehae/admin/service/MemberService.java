package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.admin.dao.CouponMapper;
import com.spoons.sehaehae.admin.dao.MemberMapper;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberMapper memberMapper;
    public MemberService(MemberMapper memberMapper){this.memberMapper=memberMapper;}
    public MemberDTO selectMemberList() {
        return memberMapper.selectMemberList();
    }
}
