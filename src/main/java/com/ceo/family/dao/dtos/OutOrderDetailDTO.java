package com.ceo.family.dao.dtos;

import lombok.Data;

@Data
public class OutOrderDetailDTO {
    private long id;
    private long createdAt;
    private long updatedAt;
    private int status;
    private String standard;
    private long price;
    private String model;
    private long buyCount;
    private long outOrderId;
}
