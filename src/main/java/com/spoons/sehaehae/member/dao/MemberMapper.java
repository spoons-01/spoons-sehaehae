package com.spoons.sehaehae.member.dao;

import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MyOrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    List<MemberDTO> selectMemberList();

    List<MyOrderDTO> findMyOrder(String memberId);
}
