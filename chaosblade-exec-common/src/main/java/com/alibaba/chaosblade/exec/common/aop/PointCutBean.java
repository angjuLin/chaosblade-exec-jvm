/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.chaosblade.exec.common.aop;

import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;

/**
 * @author Changjun Xiao
 */
public class PointCutBean implements PointCut, ExtPointCut {
    private ClassMatcher classMatcher;
    private MethodMatcher methodMatcher;

    boolean isIncludeSubClasses = false;
    boolean isIncludeBootstrap = false;

    public PointCutBean(PointCut pointCut) {
        if (pointCut != null) {
            this.classMatcher = pointCut.getClassMatcher();
            this.methodMatcher = pointCut.getMethodMatcher();
            if (pointCut instanceof SubClassesPointCut) {
                this.isIncludeSubClasses = true;
            }
            if (pointCut instanceof BootStrapPointCut) {
                this.isIncludeBootstrap = true;
            }
        }
    }



    @Override
    public ClassMatcher getClassMatcher() {
        return this.classMatcher;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }

    @Override
    public boolean isIncludeSubClasses() {
        return this.isIncludeSubClasses;
    }

    @Override
    public boolean isIncludeBootstrap() {
        return this.isIncludeBootstrap;
    }
}
