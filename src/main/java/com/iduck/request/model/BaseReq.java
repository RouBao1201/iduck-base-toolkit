package com.iduck.request.model;

import com.iduck.page.model.BasePage;

/**
 * 基础请求体
 *
 * @author songYanBin
 * @since 2022/11/24
 */
public class BaseReq {
    BasePage pageInfo;

    public BasePage getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(BasePage pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "BaseReq{" +
                "pageInfo=" + pageInfo +
                '}';
    }
}
