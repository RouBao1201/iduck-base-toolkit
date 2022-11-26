package com.iduck.exception.model;

import com.iduck.common.constant.SymbolConst;

/**
 * 基础异常类
 *
 * @author songYanBin
 * @since 2022/11/24
 */
public class BaseException extends RuntimeException {
    private String code;

    private String localMsg;

    public BaseException() {
    }

    public BaseException(String code, String localMsg) {
        this.code = code;
        this.localMsg = localMsg;
    }

    public BaseException(String message, String code, String localMsg) {
        super(message);
        this.code = code;
        this.localMsg = localMsg;
    }

    @Override
    public String getMessage() {
        return getCode()
                + SymbolConst.HORIZONTAL_LINE
                + "自定义错误信息："
                + getLocalMsg()
                + System.lineSeparator()
                + "详细错误："
                + super.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocalMsg() {
        return localMsg;
    }

    public void setLocalMsg(String localMsg) {
        this.localMsg = localMsg;
    }
}
