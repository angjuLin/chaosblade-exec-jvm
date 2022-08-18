package com.alibaba.chaosblade.exec.plugin.http.model;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;
import com.alibaba.chaosblade.exec.plugin.http.HttpConstant;

/**
 * @author angju
 * @date 2022/8/18 15:00
 */
public class ApacheAsyncHttpClientMatcherSpec extends BasePredicateMatcherSpec {
    @Override
    public String getName() {
        return HttpConstant.APACHE_ASYNC_HTTP_TARGET_NAME;
    }

    @Override
    public String getDesc() {
        return "To tag apache async httpclient experiment.";
    }

    @Override
    public boolean noArgs() {
        return true;
    }

    @Override
    public boolean required() {
        return false;
    }
}
