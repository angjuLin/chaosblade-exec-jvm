package com.alibaba.chaosblade.exec.plugin.kafka.consumer;

import com.alibaba.chaosblade.exec.common.aop.AfterEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.constant.ModelConstant;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.PradarServiceWrapper;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.plugin.kafka.KafkaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ljzhxx@gmail.com
 */
public class KafkaConsumerEnhancer extends AfterEnhancer implements KafkaConstant {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerEnhancer.class);

    @Override
    public EnhancerModel doAfterAdvice(ClassLoader classLoader, String className, Object object, Method method, Object[] methodArguments, Object returnObject) throws Exception {
        if (methodArguments == null || object == null || returnObject == null) {
            LOGGER.warn("The necessary parameter is null.");
            return null;
        }

        Boolean recordEmpty = ReflectUtil.invokeMethod(returnObject, "isEmpty", new Object[]{}, false);
        if (recordEmpty == null) {
            LOGGER.warn("KafkaConsumerEnhancer isEmpty methodName not exists");
            return null;
        }
        //没有数据也直接返回
        if (recordEmpty) {
            return null;
        }

        HashSet<String> topicKeySet = new HashSet<>();
        if (POLL.equals(method.getName())) {
            Object metadata = ReflectUtil.getFieldValue(object, "metadata", false);
            Object subscription = ReflectUtil.getFieldValue(metadata, "subscription", false);
            if (subscription != null) {
                Object subscriptionList = ReflectUtil.getFieldValue(subscription, "subscription", false);
                if (subscriptionList != null) {
                    topicKeySet = (HashSet<String>) subscriptionList;
                }
            }else{
                Map<String, Object> topics = (Map<String, Object>) ReflectUtil.getFieldValue(metadata, "topics", false);
                Iterator<String> iterator = topics.keySet().iterator();
                while (iterator.hasNext()){
                    topicKeySet.add(iterator.next());
                }
            }
        }

        //TODO 暂时不支持同一个消费组 消费多个topic，压测标需要根据topic判断，所以不同的topic压测标有可能不一致
        if (topicKeySet.size() > 1) {
            LOGGER.warn("KafkaConsumer one consumer subscribe two or more topic, not support");
            return null;
        }

        MatcherModel matcherModel = new MatcherModel();
        String topicTemp = null;
        for (String item : topicKeySet) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("consumer topicKey: {}, matcherModel: {}", item, matcherModel.getMatchers());
            }
            matcherModel.add(TOPIC_KEY, item);
            topicTemp = item;
        }

        matcherModel.add(ModelConstant.CLUSTER_TEST, PradarServiceWrapper.isClusterTestPrefix(topicTemp));

        EnhancerModel enhancerModel = new EnhancerModel(classLoader, matcherModel);
        enhancerModel.addMatcher(CONSUMER_KEY, "true");
        return enhancerModel;
    }
}

