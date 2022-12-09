package com.iduck.page.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iduck.exception.model.BusinessException;
import com.iduck.exception.util.IExceptionHandler;
import com.iduck.request.model.BaseReq;
import com.iduck.response.enums.RespEnum;
import com.iduck.page.model.BasePage;
import com.iduck.response.model.BaseResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 分页查询工具类
 *
 * @author songYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
public class IPageHelper {
    private static final Logger log = LoggerFactory.getLogger(IPageHelper.class);

    /**
     * 分页查询
     *
     * @param select    查询mapper
     * @param req       请求体
     * @param respClass 响应类
     * @param <T1>      extends BaseReq
     * @param <T2>      响应体数据类型
     * @return 公共基础分页查询返回对象
     */
    public static <T1 extends BaseReq, T2> BaseResp<List<T2>> doStart(ISelect select, T1 req, Class<T2> respClass) {
        BasePage reqPage = req.getPageInfo();
        if (ObjUtil.isEmpty(reqPage)) {
            log.error("PageHandler => The request parameter[PageInfo] cannot be null.");
            IExceptionHandler.pushExc(RespEnum.FAIL.getCode(), RespEnum.FAIL.getMessage(), BusinessException.class);
        }

        // 执行分页查询mapper
        PageInfo<T2> resultList = PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize())
                .doSelectPageInfo(select);

        // 创建统一分页返回对象
        BaseResp<List<T2>> pageResult = new BaseResp<>();
        pageResult.setData(resultList.getList());

        // 创建基础分页信息对象
        BasePage respPage = new BasePage();
        BeanUtil.copyProperties(resultList, respPage);
        pageResult.setPageInfo(respPage);

        // 设置返回体信息
        pageResult.setCode(RespEnum.SUCCESS.getCode());
        pageResult.setMessage(RespEnum.SUCCESS.getMessage());
        pageResult.setRespTime(new Date());
        return pageResult;
    }
}
