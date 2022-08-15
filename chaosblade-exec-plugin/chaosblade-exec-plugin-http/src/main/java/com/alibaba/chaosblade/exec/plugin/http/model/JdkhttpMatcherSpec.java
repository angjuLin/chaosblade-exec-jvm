package com.alibaba.chaosblade.exec.plugin.http.model;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;
import com.alibaba.chaosblade.exec.plugin.http.HttpConstant;

/**
 * @author angju
 * @date 2022/8/11 18:03
 */
public class JdkhttpMatcherSpec extends BasePredicateMatcherSpec {
    @Override
    public String getName() {
        return HttpConstant.JDKHTTP;
    }

    @Override
    public String getDesc() {
        return "To tag jdkhttp experiment.";
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
