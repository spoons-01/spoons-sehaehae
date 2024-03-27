package com.spoons.sehaehae.member.dao;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.admin.dto.RefundDTO;
import com.spoons.sehaehae.board.dto.ReplyDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.member.dto.*;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberId(String memberId);

    String selectMemberById(String memberId);

    MemberDTO findMemberEmail(String kakaoEmail);

    int insertMember(MemberDTO member);

    int insertMemberRole();

    int insertMemberLevel();

    String findMemberIdByPhoneNumber(String phoneNumber);

    @Transactional
    int updateMember(MemberDTO modifyMember);

    @Transactional
    void insertThumbnailContent(MemberDTO modifyMember);

    List<MyOrderDTO> findMyOrder(String memberId);

    List<MyCouponDTO> findMyCoupon(int memberNo);

    int findMyPoint(int memberNo);

    List<ReviewDTO> findMyReview(int memberNo);

    List<ReplyDTO> findMyReply(int memberNo);

    MyOrderDTO findMyOrderDetails(String orderCode);

    List<MyOrderProductDTO> findMyProduct(String orderCode);

    MyRefundDTO findMyRefund(String orderCode);

    int saveRefund(MyRefundDTO refund);

    List<MyOrderDTO> findByConditions(@Param("searchCondition") String searchCondition, @Param("searchStatusCondition") String searchStatusCondition);


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

    void insertFirstCoupon();

    void insertFirstPoint();

    void updateMemberPassword(@Param("email") String email, @Param("password") String password);
}
