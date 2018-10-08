package com.ceo.family.controller;

import com.ceo.family.dao.dtos.InOrderDTO;
import com.ceo.family.dao.dtos.InOrderStandardDTO;
import com.ceo.family.service.InOrderService;
import com.ceo.family.service.interf.IInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/in")
public class InOrderController {

    @Autowired
    private IInOrderService inOrderService;

    @GetMapping(value="/new")
    @ResponseStatus(value= HttpStatus.OK)
    public String create(){
        return "inOrder/new";
    }

    @PostMapping(value = "")
    @ResponseStatus(value= HttpStatus.CREATED)
    public String saveOrUpdate(@RequestBody InOrderDTO dto, Model model){
        dto.setStoneDate(dto.getStoneDate()/1000);
        dto = inOrderService.saveOrUpdate(dto);
        return "user/userNew";
    }
}
