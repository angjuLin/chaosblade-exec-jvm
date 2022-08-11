package com.alibaba.chaosblade.exec.plugin.http.okhttp3;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.OrMethodMatcher;

/**
 * okhttp3 的注入位点
 *
 * @author pengpj
 * @date 2020-7-13
 */
public class Okhttp3PointCut implements PointCut {

    @Override
    public ClassMatcher getClassMatcher() {
        return new NameClassMatcher("okhttp3.RealCall");
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        OrMethodMatcher orMethodMatcher = new OrMethodMatcher();
        orMethodMatcher.or(new NameMethodMatcher("execute"))
                .or(new NameMethodMatcher("enqueue"));
        return orMethodMatcher;
//        return new NameMethodMatcher("execute");
    }
}
