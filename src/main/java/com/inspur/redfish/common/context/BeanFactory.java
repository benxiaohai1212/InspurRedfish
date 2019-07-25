/*
 * Copyright (c) 2015-2018 Intel Corporation
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

package com.inspur.redfish.common.context;


import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class BeanFactory implements Serializable {
    private static final long serialVersionUID = -6691592134302488258L;
    
    /**
     * 最新写法，所有需要BeanFactory创建的对象必须声明为多例@Scope("prototype")，然后每次创建时从容器获取一个对象
     * 这样能够保证对象被spring代理，同时后续应该可以被垃圾回收
     */
    public <T> T create(Class<T> beanClass) {
    	T bean = (T) AppContext.context().getBean(beanClass);
    	Map<String, T> beansOfType = AppContext.context().getBeansOfType(beanClass);
    	System.out.println("Create bean for " + beanClass.getName() + ", and now the object number is:" + beansOfType.size());
    	return bean;
    }
}
