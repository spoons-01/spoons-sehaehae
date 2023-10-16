package com.spoons.sehaehae.member.dao;

import com.spoons.sehaehae.admin.dto.RefundDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MyCouponDTO;
import com.spoons.sehaehae.member.dto.MyOrderDTO;
import com.spoons.sehaehae.member.dto.MyPointDTO;
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

    List<MyOrderDTO> findMyOrder(String memberId);

    List<MyCouponDTO> findMyCoupon(int memberNo);

    int findMyPoint(int memberNo);

    List<ReviewDTO> findMyReview(int memberNo);



    /* 동한님 */

    List<MemberDTO> selectMemberList();

    void adUpdateMember(MemberDTO member);

    void removeMember(MemberDTO removeMember);


    int getfNum();

    int getmNum();

    int getFirst();

    int getSecond();

    int getThird();

    int getFourth();

    int getFifth();



}
