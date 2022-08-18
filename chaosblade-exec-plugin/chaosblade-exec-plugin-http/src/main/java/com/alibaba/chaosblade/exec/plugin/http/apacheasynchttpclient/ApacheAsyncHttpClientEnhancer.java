package com.alibaba.chaosblade.exec.plugin.http.apacheasynchttpclient;

import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.plugin.http.HttpEnhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

import static com.alibaba.chaosblade.exec.plugin.http.HttpConstant.APACHE_ASYNC_HTTP_TARGET_NAME;
import static com.alibaba.chaosblade.exec.plugin.http.HttpConstant.DEFAULT_TIMEOUT;

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
            LOGGER.error("ApacheAsyncHttpClientEnhancer getTimeout error {}", e);
            return DEFAULT_TIMEOUT;
        }
    }

    @Override
    protected String getUrl(Object instance, Object[] object) throws Exception {
        URI uri = (URI) ReflectUtil.invokeMethod(object[0], "getURI", new Object[]{}, false);
        if (uri != null) {
            return getService(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());
        } else {
            Object request = ReflectUtil.getSuperclassFieldValue(object[0], "request", false);
            uri = (URI) ReflectUtil.invokeMethod(request, "getURI", new Object[]{}, false);
            if (uri != null) {
                return getService(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());
            }
        }
        return null;
    }

    @Override
    protected Set<String> getUrls(Object instance, Object[] object) throws Exception{
        Object hostname = ReflectUtil.getFieldValue(object[0], "hostname", false);
        if (hostname != null) {
            Set<String> urls = new HashSet<String>();
            //这种写法可以一次请求多个地址
            Object port = ReflectUtil.getFieldValue(object[0], "port", false);
            Object schemeName = ReflectUtil.getFieldValue(object[0], "schemeName", false);
            for (Object target : (AbstractList)object[1]) {
                URI uri = (URI) ReflectUtil.invokeMethod(target, "getURI", new Object[]{}, false);
                urls.add(getService(schemeName.toString(), hostname.toString(), Integer.valueOf(port.toString()), uri.getPath()));
            }
            return urls;
        }
        return null;
    }

    private String getService(String schema, String host, int port, String path) {
        String url = schema + "://" + host;
        if (port != -1 && port != 80) {
            url = url + ':' + port;
        }
        if (path != null) {
            return url + path;
        }
        return url;
    }
}
