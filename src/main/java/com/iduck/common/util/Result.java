package com.iduck.common.util;

/**
 * 结果对象
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
public class Result {
    private static final String DEFAULT_SUCCESS_CODE = "0";

    private static final String DEFAULT_SUCCESS_MESSAGE = "success";

    private static final String DEFAULT_FAIL_CODE = "1";

    private static final String DEFAULT_FAIL_MESSAGE = "fail";

    private static final String DEFAULT_PART_CHAR = ";";

    private boolean ack;

    private String code;

    private StringBuilder message;

    private Object data;

    /**
     * 构建成功对象
     *
     * @return Result
     */
    public static Result ok() {
        return build(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    /**
     * 构建成失败对象
     *
     * @return Result
     */
    public static Result no() {
        return build(true, DEFAULT_FAIL_CODE, DEFAULT_FAIL_MESSAGE, null);
    }

    /**
     * 自定义构建对象
     *
     * @return Result
     */
    public static Result build(boolean ack, String code, String message, Object data) {
        Result result = new Result();
        result.setAck(ack);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 校验当前对象是否为失败
     *
     * @return boolean
     */
    public boolean isNo() {
        return !this.isAck();
    }

    /**
     * 校验当前对象是否为成功
     *
     * @return boolean
     */
    public boolean isOk() {
        return this.isAck();
    }

    /**
     * 将失败对象转换为成功
     */
    public void transOk() {
        if (!this.isAck()) {
            this.setAck(true);
            this.setCode(DEFAULT_SUCCESS_CODE);
            this.message.setLength(0);
            this.message.append(DEFAULT_SUCCESS_MESSAGE);
        }
    }

    /**
     * 将成功对象转换为失败
     */
    public void transNo() {
        if (this.isAck()) {
            this.setAck(false);
            this.setCode(DEFAULT_FAIL_CODE);
            this.message.setLength(0);
            this.message.append(DEFAULT_FAIL_MESSAGE);
        }
    }

    /**
     * 往对象信息中追加信息,默认分隔符为";"
     *
     * @param message 追加的信息
     */
    public void appendMsg(String message) {
        this.appendMsg(message, DEFAULT_PART_CHAR);
    }

    /**
     * 往对象信息中追加信息
     *
     * @param message  追加的信息
     * @param partChar 分隔符
     */
    public void appendMsg(String message, String partChar) {
        this.message.append(partChar).append(message);
    }

    /**
     * 更新信息
     *
     * @param message 新的信息
     */
    public void refreshMsg(String message) {
        this.message.setLength(0);
        this.message.append(message);
    }

    /**
     * 更新信息
     *
     * @param data 数据
     */
    public void refreshData(Object data) {
        this.data = data;
    }

    public boolean isAck() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return String.valueOf(message);
    }

    public void setMessage(String message) {
        this.message = new StringBuilder(message);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "ack=" + ack +
                ", code='" + code + '\'' +
                ", message=" + message +
                ", data=" + data +
                '}';
    }

    private Result() {

    }
}
