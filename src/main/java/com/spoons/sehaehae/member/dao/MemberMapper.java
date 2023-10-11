package com.spoons.sehaehae.member.dao;

import com.spoons.sehaehae.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberId(String memberId);
    String selectMemberById(String memberId);
    int insertMember(MemberDTO member);
    int insertMemberRole();
    int insertMemberLevel();
    int countMemberByTel(String tel);
    @Transactional


    int updateMember(MemberDTO modifyMember);
    @Transactional
    void insertThumbnailContent(MemberDTO modifyMember);
}
