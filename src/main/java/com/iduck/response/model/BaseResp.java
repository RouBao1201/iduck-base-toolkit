package com.iduck.response.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.iduck.page.model.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础响应体
 *
 * @author songYanBin
 * @since 2022/11/24
 */
public class BaseResp<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private Date respTime;

    private BasePage pageInfo;

    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String throwErrorMsg;

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

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public BasePage getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(BasePage pageInfo) {
        this.pageInfo = pageInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getThrowErrorMsg() {
        return throwErrorMsg;
    }

    public void setThrowErrorMsg(String throwErrorMsg) {
        this.throwErrorMsg = throwErrorMsg;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", respTime=" + respTime +
                ", pageInfo=" + pageInfo +
                ", data=" + data +
                ", throwErrorMsg='" + throwErrorMsg + '\'' +
                '}';
    }
}
