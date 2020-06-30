package com.sunsun.threadpooldemo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Caroline SUN
 * @version 1.0.0
 * @description TODO
 * @date 2020-06-30 13:55
 **/
public class MyTask implements Runnable
{
	private final static Logger logger = LoggerFactory
			.getLogger(MyTask.class);

	private int taskId;
	private String taskName;

	public MyTask(int taskId, String taskName)
	{
		this.taskId = taskId;
		this.taskName = taskName;
	}

	public int getTaskId()
	{
		return taskId;
	}

	public void setTaskId(int taskId)
	{
		this.taskId = taskId;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	@Override
	public void run()
	{
		try
		{
			logger.info(
					"run taskId = {}， Thread {}", this.taskId, Thread
							.currentThread().getId());
			Thread.sleep(5 * 1000);
//			logger.info("end taskId = {},currentTime:{}", this.taskId,
//					System.currentTimeMillis());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public String toString()
	{
		return Integer.toString(this.taskId);
	}
}
