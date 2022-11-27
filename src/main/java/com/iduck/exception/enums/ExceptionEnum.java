package com.iduck.exception.enums;

/**
 * 异常枚举
 *
 * @author songYanBin
 * @since 2022/11/14
 */
public enum ExceptionEnum {

    VALID_EXCEPTION("814400", "参数异常"),

    BUSI_EXCEPTION("814500", "业务异常"),

    AUTH_EXCEPTION("814501", "鉴权异常");

    private String code;

    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
