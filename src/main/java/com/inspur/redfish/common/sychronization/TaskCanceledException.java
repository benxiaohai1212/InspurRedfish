/*
 * Copyright (c) 2017-2018 Intel Corporation
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

package com.inspur.redfish.common.sychronization;

import javax.ejb.ApplicationException;

//个人感觉这个注解可以暂时不用考虑了，因为抛出这个异常是在DiscoveryRunner的122行，抓取数据时抛出，然后在130行被catch，应该不会有事务回滚之类的问题
//这个注解的作用是为了回滚全部还是回滚局部？
//请参考https://stackoverflow.com/questions/8490684/ejb-avoid-transaction-rollback
@ApplicationException(rollback = true)
public class TaskCanceledException extends RuntimeException {
    private static final long serialVersionUID = -92408671778570222L;

    public TaskCanceledException(String message) {
        super(message);
    }
}
