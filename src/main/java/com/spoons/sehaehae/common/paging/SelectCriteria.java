package com.spoons.sehaehae.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class SelectCriteria {
    private int page;  // 현재 요청하는 페이지
    private int totalCount;   // 현재의 검색조건을 포함해서 노출 하고자 하는 컨텐츠 갯수
    private int limit;  // 내가 홈페이지에 보여주고자 하는 () 개수
    private int buttonAmount;  // 내가 보여주고자 하는 버튼의 갯수
    private int maxPage;  // 총 페이지
    private int startPage;  // 요청하는 페이지가 1이라면 1
    private int endPage;   //그럼 여기는 5
    private int startRow;
    private int endRow;
    private String searchCondition;
    private String searchValue;
//    private String startDate;
//    private String endDate;

}
