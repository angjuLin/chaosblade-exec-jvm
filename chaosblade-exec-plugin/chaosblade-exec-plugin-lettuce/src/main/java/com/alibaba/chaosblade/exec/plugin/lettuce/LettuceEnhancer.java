package com.alibaba.chaosblade.exec.plugin.lettuce;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.constant.ModelConstant;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.JsonUtil;
import com.alibaba.chaosblade.exec.common.util.PradarServiceWrapper;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

import static com.alibaba.chaosblade.exec.plugin.lettuce.LettuceConstants.CMD;
import static com.alibaba.chaosblade.exec.plugin.lettuce.LettuceConstants.KEY;

/**
 * @author yefei
 */
public class LettuceEnhancer extends BeforeEnhancer {

    private static final Logger logger = LoggerFactory.getLogger(LettuceEnhancer.class);

    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader,
                                        String className,
                                        Object object,
                                        Method method,
                                        Object[] methodArguments) throws Exception {
        Object command = methodArguments[1];
        Object args = ReflectUtil.getFieldValue(command, "command", false);
        Object commandType = ReflectUtil.getFieldValue(args, "type", false);

        Object commandArgs = ReflectUtil.getFieldValue(args, "args", false);
        List singularArguments = ReflectUtil.getFieldValue(commandArgs, "singularArguments", false);
        Object keyArgument = singularArguments.get(0);
        MatcherModel matcherModel = new MatcherModel();
        if (keyArgument == null) {
            return null;
        }
        Object key = ReflectUtil.getFieldValue(keyArgument, "key", false);
        //注意 lettuce 切的点是netty 数据out的切点，这里只能根据keyArgument的前缀判断，所以演练流量不支持黑名单配置，否则无法识别流量
        try {
            String keyTemp;
            if (key instanceof byte[]) {
                keyTemp = new String((byte[])key);
            } else if (key instanceof char[]) {
                keyTemp = new String((char[])key);
            } else {
                keyTemp = String.valueOf(key);
            }
            matcherModel.add(ModelConstant.CLUSTER_TEST, PradarServiceWrapper.isClusterTestPrefix(keyTemp));
        } catch (Throwable ignore) {}
        matcherModel.add(KEY, key);
        matcherModel.add(CMD, commandType);
        logger.debug("lettuce matchers: {}", JsonUtil.writer().writeValueAsString(matcherModel));
        return new EnhancerModel(classLoader, matcherModel);
    }
}