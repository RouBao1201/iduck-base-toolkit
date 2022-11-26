package com.iduck.response.util;

import com.iduck.response.enuns.RespEnum;
import com.iduck.response.model.BaseResp;

import java.util.Date;

/**
 * 响应体构造器
 *
 * @author songYanBin
 * @since 2022/11/24
 */
public class RespBuilder {

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T>  响应数据枚举类型
     * @return BaseResp
     */
    public static <T> BaseResp<T> success(T data) {
        return build(RespEnum.SUCCESS.getCode(), RespEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 失败响应
     *
     * @param data 响应数据
     * @param <T>  响应数据枚举类型
     * @return BaseResp
     */
    public static <T> BaseResp<T> fail(T data) {
        return build(RespEnum.FAIL.getCode(), RespEnum.FAIL.getMessage(), data);
    }

    /**
     * 异常响应
     *
     * @param data 响应数据
     * @param <T>  响应数据枚举类型
     * @return BaseResp
     */
    public static <T> BaseResp<T> error(T data) {
        return build(RespEnum.ERROR.getCode(), RespEnum.ERROR.getMessage(), data);
    }

    /**
     * 基础响应
     *
     * @param code    响应编码
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     响应数据枚举类型
     * @return BaseResp
     */
    public static <T> BaseResp<T> build(String code, String message, T data) {
        BaseResp<T> resp = new BaseResp<>();
        resp.setData(data);
        resp.setRespTime(new Date());
        resp.setCode(code);
        resp.setMessage(message);
        return resp;
    }

    private RespBuilder() {

    }
}
