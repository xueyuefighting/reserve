package com.ceo.family.dao.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserParam {
    private String name;
    private long tel;
    private int total;
    private int page;
    public UserParam(String name, long tel){
        this.name = name;
        this.tel = tel;
    }
    public UserParam(){
    }
}
