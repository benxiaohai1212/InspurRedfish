package com.inspur.redfish.detection.config;
/**
 * detect的相关配置文件,定时检查redfish服务是否还处于连通状态.
 * @author 86135
 *
 */
public class RedfishDetectConfig {
    private static long filesCheckIntervalInSeconds = 10L;

	public static long getFilesCheckIntervalInSeconds() {
		return filesCheckIntervalInSeconds;
	}

	public static void setFilesCheckIntervalInSeconds(long filesCheckIntervalInSeconds) {
		RedfishDetectConfig.filesCheckIntervalInSeconds = filesCheckIntervalInSeconds;
	}
}
