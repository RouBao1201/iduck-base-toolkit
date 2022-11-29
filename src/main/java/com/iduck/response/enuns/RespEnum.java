package com.iduck.response.enuns;

/**
 * 响应枚举
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
public enum RespEnum {
    SUCCESS("0", "成功"),

    FAIL("1", "失败"),

    ERROR("2", "异常");

    private String code;

    private String message;

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

    RespEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
