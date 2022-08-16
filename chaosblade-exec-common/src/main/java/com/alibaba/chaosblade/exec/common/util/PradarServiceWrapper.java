package com.alibaba.chaosblade.exec.common.util;

import com.pamirs.pradar.PradarService;

/**
 * @author angju
 * @date 2022/8/11 10:27
 * 直接调用PradarService 没有接linkagent 探针的情况下，抛出classNotFound异常
 */
public class PradarServiceWrapper {
    /**
     * 是否压测
     * @return
     */
    public static boolean isClusterTest() {
        try {
            return PradarService.isClusterTest();
        } catch (Throwable ignore) {
            return false;
        }
    }

    /**
     * 给定的value是否以压测标开头
     * @param value
     * @return
     */
    public static boolean isClusterTestPrefix(String value) {
        try {
            if (value == null) {
                return false;
            }
            return PradarService.isClusterTestPrefix(value);
        } catch (Throwable ignore) {
            return false;
        }
    }
}
