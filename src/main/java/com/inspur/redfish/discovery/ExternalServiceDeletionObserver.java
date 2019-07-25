/*
 * Copyright (c) 2016-2018 Intel Corporation
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

package com.inspur.redfish.discovery;


import javax.enterprise.event.TransactionPhase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.event.TransactionalEventListener;

import com.inspur.redfish.common.sychronization.SerialExecutorRegistry;

@Component
class ExternalServiceDeletionObserver {

    @Autowired
    private SerialExecutorRegistry serialExecutorRegistry;

    //TODO 需要处理相关事件
    
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    @EventListener
//    public void onExternalServiceDeletion(ExternalServiceDeletionEvent externalServiceDeletionEvent) {
//    serialExecutorRegistry.unregisterExecutor(externalServiceDeletionEvent.getUuid());
//    }
}

