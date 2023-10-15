package com.spoons.sehaehae.member.service;


import com.spoons.sehaehae.common.exception.member.MemberModifyException;
import com.spoons.sehaehae.common.exception.member.MemberRegistException;
import com.spoons.sehaehae.member.dao.MemberMapper;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MyCouponDTO;
import com.spoons.sehaehae.member.dto.MyOrderDTO;
import com.spoons.sehaehae.member.util.Naver_Sens_V2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /* 내 쿠폰 목록 */
    public List<MyCouponDTO> findMyCoupon(int memberNo) {
        List<MyCouponDTO> myCoupons = memberMapper.findMyCoupon(memberNo);
        return myCoupons;
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

        if(!(result1 > 0 && result2 > 0 && result3 > 0)) throw new MemberRegistException("회원 가입에 실패하였습니다.");

    }

    @Transactional
    public void modifyMember(MemberDTO modifyMember) throws MemberModifyException {

        //memberMapper.insertThumbnailContent(modifyMember);
        int result = memberMapper.updateMember(modifyMember);

        if(!(result > 0)) {throw new MemberModifyException("회원 정보 수정에 실패하였습니다.");}
    }




    /* SENS 난수 생성 */
    public String sendRandomMessage(String tel) {
        Naver_Sens_V2 message = new Naver_Sens_V2();
        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 6; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        System.out.println("회원가입 문자 인증 => " + numStr);

        message.send_msg(tel, numStr);

        return numStr;
    }


    /* 중복된 핸드폰 번호인지 확인 */
    public int memberTelCount(String tel) {
        return memberMapper.countMemberByTel(tel);
    }



}
