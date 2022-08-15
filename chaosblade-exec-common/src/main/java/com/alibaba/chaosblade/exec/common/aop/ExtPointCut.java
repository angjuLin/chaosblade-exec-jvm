package com.alibaba.chaosblade.exec.common.aop;

/**
 * @author angju
 * @date 2022/8/12 17:30
 */
public interface ExtPointCut {

    boolean isIncludeSubClasses();

    boolean isIncludeBootstrap();
}
