package com.alibaba.chaosblade.exec.plugin.druid;

import com.alibaba.chaosblade.exec.common.aop.PredicateResult;
import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.Model;
import com.alibaba.chaosblade.exec.common.model.action.connpool.ConnectionPoolFullActionSpec;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Changjun Xiao
 */
public class DruidModelSpec extends FrameworkModelSpec  {

    public DruidModelSpec() {
        super();
    }

    @Override
    public String getTarget() {
        return DruidConstant.TARGET_NAME;
    }

    @Override
    public String getShortDesc() {
        return "Experiment with the Druid";
    }

    @Override
    public String getLongDesc() {
        return "Experiment with the Druid database connection pool, For example `blade create druid connectionpoolfull`";
    }

    @Override
    protected PredicateResult preMatcherPredicate(Model matcherSpecs) {
        return PredicateResult.success();
    }



    private void addConnectionPoolFullAction() {
        ConnectionPoolFullActionSpec actionSpec = new ConnectionPoolFullActionSpec(DruidConnectionPoolFullExecutor.INSTANCE);
        actionSpec.setExample("# Do a full load experiment on the Druid database connection pool\n" +
                "blade create druid connectionpoolfull");
        addActionSpec(actionSpec);
    }

    //druid 暂时不对sql 或者表什么的进行匹配，只按照压测流量
    @Override
    protected List<MatcherSpec> createNewMatcherSpecs() {
        ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
        return matcherSpecs;
    }
}
