package com.ceo.family.dao.dtos;

import com.ceo.family.constant.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
/**
 * @author zhangjinliang
 * @Date 2018/10/5 上午9:09
*/
public class UserDTO {
    private long id;
    private long createdAt;
    private long updatedAt;
    private String name;
    private Status status;
    private long tel;
}
