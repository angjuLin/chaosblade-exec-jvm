package com.alibaba.chaosblade.exec.plugin.http.jdkhttp;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.plugin.http.HttpConstant;
import com.alibaba.chaosblade.exec.plugin.http.HttpPlugin;

/**
 * @author angju
 * @date 2022/8/11 17:33
 */
public class JdkhttpPlugin extends HttpPlugin {
    @Override
    public String getName() {
        return HttpConstant.JDKHTTP_TARGET_NAME;
    }

    @Override
    public PointCut getPointCut() {
        return new JdkhttpPointCut();
    }

    @Override
    public Enhancer getEnhancer() {
        return new JdkhttpEnhancer();
    }
}
