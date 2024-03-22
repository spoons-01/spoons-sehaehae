package com.spoons.sehaehae.member.service;


import com.spoons.sehaehae.board.dto.ReplyDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.common.exception.member.MemberModifyException;
import com.spoons.sehaehae.common.exception.member.MemberRefundException;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.member.dao.MemberMapper;
import com.spoons.sehaehae.member.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /* 카카오 로그인(이메일 찾기) */
    public MemberDTO findMemberEmail(String kakaoEmail) {
        MemberDTO member = memberMapper.findMemberEmail(kakaoEmail);
        return member;
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

    /* 주문 목록 조회 (기간별 및 상태별) */
    public List<MyOrderDTO> findByConditions(String searchCondition, String searchStatusCondition) {
        log.info("searchCondition : {}, searchStatusCondition : {}", searchCondition, searchStatusCondition);
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("orderStatus1", "결제완료");
        statusMap.put("orderStatus2", "수거완료");
        statusMap.put("orderStatus3", "세탁완료");
        statusMap.put("orderStatus4", "배송준비");
        statusMap.put("orderStatus5", "배송중");
        statusMap.put("orderStatus6", "구매확정");

        if (statusMap.containsKey(searchStatusCondition)) {
            searchStatusCondition = statusMap.get(searchStatusCondition);
        }

        return memberMapper.findByConditions(searchCondition, searchStatusCondition);
    }

    /* 내 쿠폰 목록 */
    public List<MyCouponDTO> findMyCoupon(int memberNo) {
        List<MyCouponDTO> myCoupons = memberMapper.findMyCoupon(memberNo);
        return myCoupons;
    }

    /* 내 포인트 조회 */
    public Integer findMyPoint(int memberNo) {
        Integer myPoint = memberMapper.findMyPoint(memberNo);
        return myPoint;
    }

    /* 내 후기 조회 */
    public List<ReviewDTO> findMyReview(int memberNo) {
        List<ReviewDTO> myReviews = memberMapper.findMyReview(memberNo);
        return myReviews;
    }

    /* 내 덧글 조회 */
    public List<ReplyDTO> findMyReply(int memberNo) {
        List<ReplyDTO> myReplys = memberMapper.findMyReply(memberNo);
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

        memberMapper.insertFirstCoupon();
        memberMapper.insertFirstPoint();

        if (!(result1 > 0 && result2 > 0 && result3 > 0)) throw new MemberRegistException("회원 가입에 실패하였습니다.");
    }

    @Transactional
    public void saveRefund(MyRefundDTO refund) {
        /* 성공 실패 로직 추가 */
        int result = memberMapper.saveRefund(refund);
        if (!(result > 0)) throw new MemberRefundException("환불 신청에 실패하였습니다.");
    }

    /* 회원 정보 수정 */
    @Transactional
    public void modifyMember(MemberDTO modifyMember) throws MemberModifyException {
        int result = memberMapper.updateMember(modifyMember);
        if (!(result > 0)) {
            throw new MemberModifyException("회원 정보 수정에 실패하였습니다.");
        }
    }

    /* 아이디 찾기 */
    public String searchMyId(String phoneNumber) {
        return memberMapper.findMemberIdByPhoneNumber(phoneNumber);
    }

    /* 비밀번호 재설정 */
    public void updateMemberPassword(String email, String newTempPassword) {
        String encodedPassword = passwordEncoder.encode(newTempPassword);
        memberMapper.updateMemberPassword(email, encodedPassword);
    }
}
