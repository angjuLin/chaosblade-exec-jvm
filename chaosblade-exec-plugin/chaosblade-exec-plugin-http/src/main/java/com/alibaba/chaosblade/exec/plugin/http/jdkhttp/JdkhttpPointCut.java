package com.alibaba.chaosblade.exec.plugin.http.jdkhttp;

import com.alibaba.chaosblade.exec.common.aop.BootStrapPointCut;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.OrClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.OrMethodMatcher;

/**
 * @author angju
 * @date 2022/8/11 17:33
 */
public class JdkhttpPointCut implements PointCut, BootStrapPointCut {
    @Override
    public ClassMatcher getClassMatcher() {
        OrClassMatcher orClassMatcher = new OrClassMatcher();
        orClassMatcher
//                .or(new NameClassMatcher("io.shulie.demo.client.controller.TestCut"));
                .or(new NameClassMatcher("sun.net.www.protocol.http.HttpURLConnection"));
        return orClassMatcher;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        OrMethodMatcher orMethodMatcher = new OrMethodMatcher();
        orMethodMatcher
//                .or(new NameMethodMatcher("test"))
                .or(new NameMethodMatcher("getInputStream"));
        return orMethodMatcher;
    }
}
