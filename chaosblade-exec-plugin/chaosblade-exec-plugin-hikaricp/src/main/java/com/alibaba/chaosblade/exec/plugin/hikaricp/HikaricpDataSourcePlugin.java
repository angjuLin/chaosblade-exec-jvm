package com.alibaba.chaosblade.exec.plugin.hikaricp;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.Plugin;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.model.ModelSpec;

/**
 * @author angju
 * @date 2022/8/24 17:52
 */
public class HikaricpDataSourcePlugin implements Plugin {
    @Override
    public String getName() {
        return HikaricpConstant.HIKARICP_DS_PLUGIN_NAME;
    }

    @Override
    public ModelSpec getModelSpec() {
        return null;
    }

    @Override
    public PointCut getPointCut() {
        return null;
    }

    @Override
    public Enhancer getEnhancer() {
        return null;
    }
}
