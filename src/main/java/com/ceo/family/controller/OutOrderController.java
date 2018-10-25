package com.ceo.family.controller;

import com.ceo.family.dao.dtos.InOrderDTO;
import com.ceo.family.dao.dtos.InOrderParam;
import com.ceo.family.service.interf.IInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/out")
public class OutOrderController {

    @Autowired
    private IInOrderService inOrderService;

    @GetMapping(value="/new")
    @ResponseStatus(value= HttpStatus.OK)
    public String create(){
        return "outOrder/new";
    }

    @PostMapping(value = "")
    @ResponseStatus(value= HttpStatus.CREATED)
    public String saveOrUpdate(@RequestBody InOrderDTO dto, Model model) throws Exception {
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

    @GetMapping(value="/{inId}")
    @ResponseStatus(value= HttpStatus.OK)
    public String get(@PathVariable(value = "inId") long inId, Model model){
        InOrderDTO orderDTO = inOrderService.findById(inId);
        model.addAttribute("inOrder", orderDTO);
        return "inOrder/edit";
    }
}
