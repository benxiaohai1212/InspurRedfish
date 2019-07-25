/**
 *<p> Copyright © 2018 Inspur Group Co.,Ltd.  版权所有 浪潮集团有限公司 </p>.
 */
package com.inspur.redfish.common.concurrent.custom;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.inspur.redfish.common.context.BeanNames;


/**
 * @ClassName: ExecutorConfig
 * @Description: TODO
 *
 * @author: liuchangbj
 * @date: 2018年12月21日 上午9:52:11
 */
@Configuration
@EnableAsync
public class ExecutorConfig {
	@Bean(name = BeanNames.SYNCHRONIZED_TASK_EXECUTOR)
	public ScheduledExecutorService getTaskExecutorService() {
		ScheduledExecutorService taskExecutorService = Executors.newScheduledThreadPool(10);
		return taskExecutorService;
	}
	
	@Bean(name = BeanNames.MANAGED_EXECUTOR)
	public ScheduledExecutorService getManagedExecutorService() {
		ScheduledExecutorService managedExecutorService = Executors.newScheduledThreadPool(10);
		return managedExecutorService;
	}
}

