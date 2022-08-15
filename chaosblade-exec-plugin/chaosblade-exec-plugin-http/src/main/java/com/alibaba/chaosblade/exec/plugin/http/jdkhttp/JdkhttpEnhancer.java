package com.alibaba.chaosblade.exec.plugin.http.jdkhttp;

import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.plugin.http.HttpEnhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.alibaba.chaosblade.exec.plugin.http.HttpConstant.DEFAULT_TIMEOUT;
import static com.alibaba.chaosblade.exec.plugin.http.HttpConstant.JDKHTTP;

/**
 * @author angju
 * @date 2022/8/11 17:33
 */
public class JdkhttpEnhancer extends HttpEnhancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdkhttpEnhancer.class);

    private static final String GET_REQUEST = "request";
    private static final String GET_URL = "url";
    private static final String GET_CONNECTION_TIMEOUT = "connectTimeoutMillis";
    private static final String GET_READ_TIMEOUT = "readTimeoutMillis";


    @Override
    protected void postDoBeforeAdvice(EnhancerModel enhancerModel) {
        enhancerModel.addMatcher(JDKHTTP, "true");
    }

    @Override
    protected Map<String, Map<String, String>> getBusinessParams(String className, final Object instance, Method method, final Object[] methodArguments) throws Exception {
        //不支持参数匹配
        return null;
    }

    @Override
    protected int getTimeout(Object instance, Object[] methodArguments) {
        try {
            int connectionTimeout = (Integer) ReflectUtil.getFieldValue(instance, "connectTimeout", false);
            int readTimeout = (Integer) ReflectUtil.getFieldValue(instance, "readTimeout", false);
            return connectionTimeout + readTimeout;
        } catch (Exception e) {
            LOGGER.warn("Getting timeout from url occurs exception. return default value {}", DEFAULT_TIMEOUT, e);
        }
        return DEFAULT_TIMEOUT;
    }

    /**
     * get url
     */
    @Override
    protected String getUrl(Object realCall, Object[] object) throws Exception {
        final HttpURLConnection request = (HttpURLConnection) realCall;
        final URL url = request.getURL();
        String fullPath = getService(url.getProtocol(),
                url.getHost(),
                url.getPort(),
                url.getPath());

        return fullPath;
    }

    private static String getService(String schema, String host, int port, String path) {
        String url = schema + "://" + host;
        if (port != -1 && port != 80) {
            url = url + ':' + port;
        }
        return url + path;
    }
}
