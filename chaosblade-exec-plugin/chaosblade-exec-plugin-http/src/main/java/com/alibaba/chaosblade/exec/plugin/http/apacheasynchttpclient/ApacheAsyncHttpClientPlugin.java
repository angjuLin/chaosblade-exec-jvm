package com.alibaba.chaosblade.exec.plugin.http.apacheasynchttpclient;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.plugin.http.HttpConstant;
import com.alibaba.chaosblade.exec.plugin.http.HttpPlugin;

/**
 * @author angju
 * @date 2022/8/18 14:55
 */
public class ApacheAsyncHttpClientPlugin extends HttpPlugin {
    @Override
    public String getName() {
        return HttpConstant.APACHE_ASYNC_HTTP_TARGET_NAME;
    }

    @Override
    public PointCut getPointCut() {
        return new ApacheAsyncHttpClientPointCut();
    }

    @Override
    public Enhancer getEnhancer() {
        return new ApacheAsyncHttpClientEnhancer();
    }
}
