package com.alibaba.chaosblade.exec.plugin.hikaricp;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.AndMethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.ParameterMethodMatcher;

/**
 * @author angju
 * @date 2022/8/24 18:46
 */
public class HikaricpDataSourcePointCut implements PointCut {
    @Override
    public ClassMatcher getClassMatcher() {
        return new NameClassMatcher("com.zaxxer.hikari.HikariDataSource");
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        // public DruidPooledConnection getConnection(long maxWaitMillis) throws SQLException
        AndMethodMatcher methodMatcher = new AndMethodMatcher();
        methodMatcher.and(new NameMethodMatcher("getConnection")).and(
                new ParameterMethodMatcher(0, ParameterMethodMatcher.EQUAL));
        return methodMatcher;
    }
}
