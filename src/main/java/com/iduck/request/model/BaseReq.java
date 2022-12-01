package com.iduck.request.model;

import com.iduck.page.model.BasePage;

import java.io.Serializable;

/**
 * 基础请求体
 *
 * @author songYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 1L;

    BasePage pageInfo;

    public BaseReq() {
    }

    public BaseReq(BasePage pageInfo) {
        this.pageInfo = pageInfo;
    }

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
