package com.ceo.family.exception;


public abstract class BaseException extends RuntimeException {

    private Integer code;

    private String target;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.target = "";
    }

    public BaseException(Integer code, String target, String message) {
        super(message);
        this.code = code;
        this.target = target;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.target = "";
    }

    public BaseException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
        this.target = "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
