package com.spoons.sehaehae.common.paging;


import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class SelectCriteria {
    private int page;
    private int totalCount;
    private int limit;
    private int buttonAmount;
    private int maxPage;
    private int startPage;
    private int endPage;
    private int startRow;
    private int endRow;
    private String searchValue;
    private String searchCondition;

}
