package com.iduck.exception.model;

import cn.hutool.core.util.StrUtil;

/**
 * 基础异常类
 *
 * @author songYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
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
        String resultStr = "CustomErrorMessage: [" + getCode() + "-" + getLocalMsg() + "]" + System.lineSeparator();

        // 避免自定义异常抛出时,super.getMessage()无数据,造成打印显示null（误导）
        if (StrUtil.isNotEmpty(super.getMessage())) {
            resultStr += "ErrorMessageDetail: [" + super.getMessage() + "]";
        }
        return resultStr;
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
