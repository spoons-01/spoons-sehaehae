package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.admin.dao.AdMemberMapper;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdMemberService {
    private final AdMemberMapper adMemberMapper;
    public AdMemberService(AdMemberMapper adMemberMapper){this.adMemberMapper = adMemberMapper;}
    public List<MemberDTO> selectMemberList() {
        return adMemberMapper.selectMemberList();
    }
}
