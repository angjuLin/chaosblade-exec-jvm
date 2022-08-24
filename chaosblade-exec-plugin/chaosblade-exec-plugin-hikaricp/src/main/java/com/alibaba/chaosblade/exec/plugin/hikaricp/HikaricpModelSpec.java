package com.alibaba.chaosblade.exec.plugin.hikaricp;

import com.alibaba.chaosblade.exec.common.aop.PredicateResult;
import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.Model;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angju
 * @date 2022/8/24 18:44
 */
public class HikaricpModelSpec extends FrameworkModelSpec {
    public HikaricpModelSpec() {
        super();
    }

    @Override
    public String getTarget() {
        return HikaricpConstant.TARGET_NAME;
    }

    @Override
    public String getShortDesc() {
        return "Experiment with the Druid";
    }

    @Override
    public String getLongDesc() {
        return "Experiment with the hikaricp delay or throw exception, For example `blade create hikaricp delay --time 5000 --pid XXXX`";
    }

    @Override
    protected PredicateResult preMatcherPredicate(Model matcherSpecs) {
        return PredicateResult.success();
    }


    //hikaricp 暂时不对sql 或者表什么的进行匹配，只按照压测流量
    @Override
    protected List<MatcherSpec> createNewMatcherSpecs() {
        ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
        return matcherSpecs;
    }
}
