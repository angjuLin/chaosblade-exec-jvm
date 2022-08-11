package com.alibaba.chaosblade.exec.common.aop.matcher.method;

import com.alibaba.chaosblade.exec.common.aop.matcher.MethodInfo;

import java.util.Set;

/**
 * @author angju
 * @date 2022/8/10 17:34
 */
public class NotInNameMethodMatcher implements MethodMatcher{
    private Set<String> excludeMethodNames;

    public NotInNameMethodMatcher(Set<String> methodNames) {
        this.excludeMethodNames = methodNames;
    }

    @Override
    public boolean isMatched(String methodName, MethodInfo methodInfo) {
        return !excludeMethodNames.contains(methodName);

    }
}
