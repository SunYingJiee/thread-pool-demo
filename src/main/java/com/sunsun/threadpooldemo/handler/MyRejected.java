package com.sunsun.threadpooldemo.handler;

import com.sunsun.threadpooldemo.task.MyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Caroline SUN
 * @version 1.0.0
 * @description 自定义任务拒绝处理器
 * @date 2020-06-30 13:56
 **/
public class MyRejected implements RejectedExecutionHandler
{
	private final static Logger logger = LoggerFactory
			.getLogger(MyRejected.class);

	public MyRejected()
	{
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
	{
		logger.info("自定义处理..");
		logger.info("当前被拒绝任务为：{}", r.toString());
	}
}
