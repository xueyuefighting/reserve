package com.ceo.family.exception;

import org.springframework.http.HttpStatus;


public class BadRequestException extends BaseException {

    public BadRequestException(String msg) {
        super(HttpStatus.BAD_REQUEST.value(),
                msg != null?msg:"非法的值");
    }
    public BadRequestException(String target, String msg) {
        super(HttpStatus.BAD_REQUEST.value(),target,
                msg != null?msg:"非法的值");
    }
}
