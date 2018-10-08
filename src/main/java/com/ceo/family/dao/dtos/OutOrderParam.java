package com.ceo.family.dao.dtos;

import lombok.Data;

@Data
public class OutOrderParam {
    private long createdTimeFrom;
    private long createdTimeTo;
    private long totalAmountMax;
    private long totalAmountMin;
    private long buyUserId;
    private long tel;
    private long dateFrom;
    private long dateTo;
    private long id;//流水号就是id
}
