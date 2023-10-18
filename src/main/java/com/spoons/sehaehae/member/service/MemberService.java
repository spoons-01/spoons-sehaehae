package com.spoons.sehaehae.member.service;


import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.board.dto.AttachmentDTO;
import com.spoons.sehaehae.board.dto.ReplyDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.common.exception.member.MemberModifyException;
import com.spoons.sehaehae.common.exception.member.MemberRefundException;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.member.dao.MemberMapper;
import com.spoons.sehaehae.member.dto.*;
import com.spoons.sehaehae.member.util.Naver_Sens_V2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    /* 내 주문 목록 */
    public List<MyOrderDTO> findMyOrder(String currentUsername) {
        List<MyOrderDTO> myOrders = memberMapper.findMyOrder(currentUsername);
        return myOrders;
    }

    /* 주문상세보기 */
    public MyOrderDTO findMyOrderDetails(String orderCode) {
        MyOrderDTO myOrders = memberMapper.findMyOrderDetails(orderCode);
        return myOrders;
    }

    /* 환불 폼 */
    public MyRefundDTO findMyRefund(String orderCode) {
        MyRefundDTO myRefund = memberMapper.findMyRefund(orderCode);
        return myRefund;
    }

    public List<MyOrderProductDTO> findMyProduct(String orderCode) {
        List<MyOrderProductDTO> myProduct = memberMapper.findMyProduct(orderCode);
        return myProduct;
    }

    /* 내 쿠폰 목록 */
    public List<MyCouponDTO> findMyCoupon(int memberNo) {
        List<MyCouponDTO> myCoupons = memberMapper.findMyCoupon(memberNo);
        return myCoupons;
    }

    /* 내 포인트 조회 */
    public int findMyPoint(int memberNo) {
        int myPoint = memberMapper.findMyPoint(memberNo);
        return myPoint;
    }

    /* 내 후기 조회 */
    public List<ReviewDTO> findMyReview(int memberNo) {
        List<ReviewDTO> myReviews = memberMapper.findMyReview(memberNo);
        return myReviews;
    }

    /* 내 덧글 조회 */
    public List<ReplyDTO> findMyReply(int memberNo) {
        List<ReplyDTO> myReplys =  memberMapper.findMyReply(memberNo);
        return myReplys;
    }

    public MemberDTO findByMemberId(String memberId) {
        return memberMapper.findByMemberId(memberId);
    }

    public boolean selectMemberById(String memberId) {

        String result = memberMapper.selectMemberById(memberId);

        return result != null;
    }

    @Transactional
    public void registMember(MemberDTO member) throws MemberRegistException {

        int result1 = memberMapper.insertMember(member);
        int result2 = memberMapper.insertMemberRole();
        int result3 = memberMapper.insertMemberLevel();
        memberMapper.insertFirstCoupon();                   //첫가입쿠폰 발급

        if (!(result1 > 0 && result2 > 0 && result3 > 0)) throw new MemberRegistException("회원 가입에 실패하였습니다.");

    }


    @Transactional
    public void saveRefund(MyRefundDTO refund) {
        /* 성공 실패 로직 추가 */
        int result = memberMapper.saveRefund(refund);
        if (!(result > 0)) throw new MemberRefundException("환불 신청에 실패하였습니다.");
    }

    // @Transactional => 기본 처리 옵션은 RuntimeException 발생할 때  Rollback COmmit ;  그 외적으로는  COmmit ;
    // 쿼리만 실행되면 Commit;
    // => RUntimeException 되어야됨.
    @Transactional
    public void modifyMember(MemberDTO modifyMember) throws MemberModifyException {
        //memberMapper.insertThumbnailContent(modifyMember);
        int result = memberMapper.updateMember(modifyMember);
        if(!(result > 0)) {throw new MemberModifyException("회원 정보 수정에 실패하였습니다.");}
    }



}
