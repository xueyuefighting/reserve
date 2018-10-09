package com.ceo.family.controller;

import com.ceo.family.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception e, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("msg", "服务出问题了");
        ResponseEntity<Map<String, String>> entity = new ResponseEntity<>(msgMap, httpStatus);
        return entity;
    }
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> baseExceptionHandler(BaseException e, HttpServletResponse response) throws Exception {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getCode());
        Map<String, String> msgMap = new HashMap<>();
        if (!StringUtils.isEmpty(e.getTarget())){
            msgMap.put(e.getTarget(), e.getMessage());
        }else{
            msgMap.put("msg", e.getMessage());
        }
        ResponseEntity<Map<String, String>> entity = new ResponseEntity<>(msgMap, httpStatus);
        return entity;    }
}
