package com.sunsun.threadpooldemo.pool;

import com.sunsun.threadpooldemo.handler.MyRejected;
import com.sunsun.threadpooldemo.task.MyTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Caroline SUN
 * @version 1.0.0
 * @description TODO
 * @date 2020-06-30 13:54
 **/
public class UseThreadPoolExecutor
{

	public static void main(String[] args)
	{

		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				1,              //coreSize
				2,              //MaxSize
				60,             //60
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(3)         //指定一种队列 （有界队列）
				//new LinkedBlockingQueue<Runnable>()
				, new MyRejected()
				//, new DiscardOldestPolicy()
		);

		MyTask mt1 = new MyTask(1, "任务1");
		MyTask mt2 = new MyTask(2, "任务2");
		MyTask mt3 = new MyTask(3, "任务3");
		MyTask mt4 = new MyTask(4, "任务4");
		MyTask mt5 = new MyTask(5, "任务5");
		MyTask mt6 = new MyTask(6, "任务6");

		pool.execute(mt1);
		pool.execute(mt2);
		pool.execute(mt3);
		pool.execute(mt4);
		pool.execute(mt5);
		pool.execute(mt6);

		pool.shutdown();
	}
}
