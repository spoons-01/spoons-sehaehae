package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberDTO selectMemberList();
}
