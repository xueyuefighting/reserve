package com.ceo.family.dao.dtos;

import com.ceo.family.constant.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class InOrderStandardDTO {
    private long id;
    private long createdAt;
    private long updatedAt;
    private long inOrderId;
    private Status status;
    private String standard;
    private long price;
}
