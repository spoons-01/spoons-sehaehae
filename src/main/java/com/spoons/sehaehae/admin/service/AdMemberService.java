package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.member.dao.MemberMapper;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdMemberService {
    private final MemberMapper MemberMapper;
    public AdMemberService(MemberMapper memberMapper){this.MemberMapper = memberMapper;}
    public List<MemberDTO> selectMemberList() {
        return MemberMapper.selectMemberList();
    }
}
