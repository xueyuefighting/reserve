package com.ceo.family.controller;

import com.ceo.family.dao.dos.UserDO;
import com.ceo.family.dao.dtos.UserDTO;
import com.ceo.family.dao.dtos.UserParam;
import com.ceo.family.service.interf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "")
    @ResponseStatus(value= HttpStatus.CREATED)
    public String saveOrUpdate(@RequestBody UserDTO userDTO, Model model){
        UserDTO userDTO1 = userService.saveOrUpdate(userDTO);
        return "user/userNew";
    }

    @GetMapping(value="/{userId}")
    @ResponseStatus(value= HttpStatus.OK)
    public String get(@PathVariable(value = "userId") long userId, Model model){
        UserDTO userDTO = userService.findById(userId);
        model.addAttribute("user", userDTO);
        return "user/userEdit";
    }

    @GetMapping(value="/nameAndtel/{name}")
    @ResponseStatus(value= HttpStatus.OK)
    public List<UserParam> getMap(@PathVariable(value = "userId") String name, Model model){
        List<UserParam> param = userService.findByNameLike(name);
//        model.addAttribute("users", param);
        return param;
    }

    @GetMapping(value="/list")
    @ResponseStatus(value= HttpStatus.OK)
    public String getList(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "tel", required = false) Long tel, int page, Model model){
        UserParam param = new UserParam();
        if(!StringUtils.isEmpty(name)){param.setName(name);}
        if(Objects.nonNull(tel)){ param.setTel(tel);}
        param.setPage(page);
        Page<UserDO> pageable = userService.findByParam(param);
        model.addAttribute("page", pageable);
        return "user/userList";
    }

    @GetMapping(value="/new")
    @ResponseStatus(value= HttpStatus.OK)
    public String create(){
        return "user/userNew";
    }
}
