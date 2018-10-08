package com.ceo.family.dao.dtos;

import com.ceo.family.constant.Status;
import com.ceo.family.dao.dos.OutOrderDetailDO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午10:07
*/
public class OutOrderDTO {
    private long id;
    private long createdAt;
    private long updatedAt;
    private Status status;
    private long totalAmount;
    private long buyUserId;
    private long date;
    private long serailNo;
    private List<OutOrderDetailDTO> oods;
}
