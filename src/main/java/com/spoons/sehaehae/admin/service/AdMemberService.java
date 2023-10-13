package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.member.dao.MemberMapper;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdMemberService {
    private final MemberMapper memberMapper;
    public AdMemberService(MemberMapper memberMapper){this.memberMapper = memberMapper;}

    public List<MemberDTO> selectMemberList() {
        return memberMapper.selectMemberList();
    }

    public void adUpdateMember(MemberDTO member) {memberMapper.adUpdateMember(member);
    }

    public void removeMember(MemberDTO removeMember) {memberMapper.removeMember(removeMember);
    }

}
