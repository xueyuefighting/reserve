package com.ceo.family.dao.dtos;

import com.ceo.family.constant.Status;
import com.ceo.family.dao.dos.InOrderStandardDO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午10:06
*/
public class InOrderDTO {
    private long id;
    private long createdAt;
    private long updatedAt;
    private String model;
    private Status status;
    private long stoneDate;
    private long totalCount;
    private long currentCount;
    private long sellCount;
    private List<InOrderStandardDTO> ios;
}
