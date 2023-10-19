package com.spoons.sehaehae.member.service;

import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Repository
@Controller
public class CouponRepository {
    private final JdbcTemplate jdbcTemplate;

    public CouponRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int countCouponsByMemberNo(int memberNo) {
        // 현재 로그인한 사용자의 Authentication 객체 얻기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MemberDTO) {
            // MemberDTO 객체에서 MEMBER_NO 얻기
            MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
            memberNo = memberDTO.getMemberNo();

            // SQL 쿼리 실행
            String sql = "SELECT COUNT(*) FROM TBL_CPBOX WHERE MEMBER_NO = ? and USE_STATUS = 'N'";
            return jdbcTemplate.queryForObject(sql, new Object[]{memberNo}, Integer.class);
        }

        return 0; // 혹시 실패할 경우 기본값 설정
    }
}

