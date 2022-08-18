package com.alibaba.chaosblade.exec.plugin.http.apacheasynchttpclient;

import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.plugin.http.HttpEnhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;

import static com.alibaba.chaosblade.exec.plugin.http.HttpConstant.*;

/**
 * @author angju
 * @date 2022/8/18 14:58
 */
public class ApacheAsyncHttpClientEnhancer extends HttpEnhancer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApacheAsyncHttpClientEnhancer.class);


    @Override
    protected void postDoBeforeAdvice(EnhancerModel enhancerModel) {
        enhancerModel.addMatcher(APACHE_ASYNC_HTTP_TARGET_NAME, "true");
    }

    @Override
    protected Map<String, Map<String, String>> getBusinessParams(String className, Object instance, Method method, final Object[] methodArguments) throws Exception {
        //不支持 参数匹配
        return null;
    }

    @Override
    protected int getTimeout(Object instance, Object[] methodArguments) {
        Object requestConfig = null;
        try {
            requestConfig = ReflectUtil.getFieldValue(instance, "defaultConfig", false);
            Integer socketTimeout = ReflectUtil.getFieldValue(requestConfig, "socketTimeout", false);
            Integer connectionTimeout = ReflectUtil.getFieldValue(requestConfig, "connectTimeout", false);
            return socketTimeout + connectionTimeout;
        } catch (Exception e) {
            LOGGER.error("getTimeout error {}", e);
            return DEFAULT_TIMEOUT;
        }
    }

    @Override
    protected String getUrl(Object instance, Object[] object) throws Exception {
        URI uri = (URI) ReflectUtil.invokeMethod(object[0], "getURI", new Object[]{}, false);
        return getService(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());
    }

    private String getService(String schema, String host, int port, String path) {
        String url = schema + "://" + host;
        if (port != -1 && port != 80) {
            url = url + ':' + port;
        }
        return url + path;
    }
}
