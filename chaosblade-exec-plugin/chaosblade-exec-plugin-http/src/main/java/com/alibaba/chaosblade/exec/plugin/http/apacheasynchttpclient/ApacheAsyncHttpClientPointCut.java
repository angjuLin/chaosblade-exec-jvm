package com.alibaba.chaosblade.exec.plugin.http.apacheasynchttpclient;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.OrClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;

/**
 * @author angju
 * @date 2022/8/18 14:53
 */
public class ApacheAsyncHttpClientPointCut implements PointCut {
    @Override
    public ClassMatcher getClassMatcher() {
        OrClassMatcher orClassMatcher = new OrClassMatcher();
        orClassMatcher
                .or(new NameClassMatcher("org.apache.http.impl.nio.client.CloseableHttpPipeliningClient"))
                .or(new NameClassMatcher("org.apache.http.impl.nio.client.CloseableHttpAsyncClient"));
        return orClassMatcher;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        NameMethodMatcher nameMethodMatcher = new NameMethodMatcher("execute");
        return nameMethodMatcher;
    }
}
