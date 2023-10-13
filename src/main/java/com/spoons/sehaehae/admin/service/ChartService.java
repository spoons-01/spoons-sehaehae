package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.admin.dao.ChartMapper;
import com.spoons.sehaehae.member.dao.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChartService {
    private final MemberMapper memberMapper;
    public ChartService(MemberMapper memberMapper){this.memberMapper = memberMapper;}

    public int getfNum() {
        return memberMapper.getfNum();
    }

    public int getmNum() {
        return memberMapper.getmNum();
    }
}
