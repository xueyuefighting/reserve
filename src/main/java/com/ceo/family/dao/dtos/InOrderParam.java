package com.ceo.family.dao.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InOrderParam {
    private long createdTimeFrom;
    private long createdTimeTo;
    private String modelLike;
    private long stoneDateFrom;
    private long stoneDateTo;
    private long totalCountMax;
    private long totalCountMin;
    private long currentCountMax;
    private long currentCountMin;
    private long sellCountMax;
    private long sellCountMin;
    private int page;
//    private long priceMax;
//    private long priceMin;
}
