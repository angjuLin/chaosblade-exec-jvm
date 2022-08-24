package com.alibaba.chaosblade.exec.plugin.hikaricp;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.constant.ModelConstant;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.PradarServiceWrapper;

import java.lang.reflect.Method;

/**
 * @author angju
 * @date 2022/8/24 18:48
 */
public class HikaricpDataSourceEnhancer extends BeforeEnhancer {
    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
                                        Object[] methodArguments) throws Exception {
        //TODO 如果设置延迟，并且接入了linkAgent，会导致2倍的延迟
        MatcherModel matcherModel = new MatcherModel();
        EnhancerModel enhancerModel = new EnhancerModel(classLoader, matcherModel);
        matcherModel.add(ModelConstant.CLUSTER_TEST, PradarServiceWrapper.isClusterTest());
        return enhancerModel;
    }
}
