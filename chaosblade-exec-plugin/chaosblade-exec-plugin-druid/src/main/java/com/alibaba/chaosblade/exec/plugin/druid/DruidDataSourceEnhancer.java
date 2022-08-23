package com.alibaba.chaosblade.exec.plugin.druid;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.constant.ModelConstant;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.PradarServiceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Changjun Xiao
 */
public class DruidDataSourceEnhancer extends BeforeEnhancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceEnhancer.class);

    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
                                        Object[] methodArguments) throws Exception {

        MatcherModel matcherModel = new MatcherModel();
        EnhancerModel enhancerModel = new EnhancerModel(classLoader, matcherModel);
        matcherModel.add(ModelConstant.CLUSTER_TEST, PradarServiceWrapper.isClusterTest());
        return enhancerModel;

        /**
         *
        //连接池打满的操作，会影响所有的流量，不支持该方式注入
        if (object != null && DataSource.class.isInstance(object)) {
            LOGGER.debug("match the druid dataSource, object: {}", className);
            DruidConnectionPoolFullExecutor.INSTANCE.setDataSource(object);
        } else {
            LOGGER.debug("the object is null or is not instance of DataSource class, object: {}", object ==
                null ? null : object.getClass().getName());
        }
        return null;
        */
    }
}
