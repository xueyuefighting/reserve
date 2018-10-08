package com.ceo.family.controller;

import com.ceo.family.dao.dos.InOrderDO;
import com.ceo.family.dao.dtos.InOrderDTO;
import com.ceo.family.dao.dtos.InOrderParam;
import com.ceo.family.dao.dtos.InOrderStandardDTO;
import com.ceo.family.service.InOrderService;
import com.ceo.family.service.interf.IInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

    @GetMapping(value="/list")
    @ResponseStatus(value= HttpStatus.OK)
    public String getList(@ModelAttribute InOrderParam param, Model model){
        Page<InOrderDTO> pageable = inOrderService.findByParam(param);
        model.addAttribute("page", pageable);

        return "inOrder/list";
    }
}
