package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDTO> selectMemberList();
}
