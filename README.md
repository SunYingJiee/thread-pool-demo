# ThreadPoolExecutor DEMO示例


### 四种线程池创建方式,底层都是依赖ThreadPoolExecutor这个方法
```$xslt
ExecutorService executorService=Executors.newFixedThreadPool(10);
ExecutorService executorService1=Executors.newCachedThreadPool();
ExecutorService executorService2=Executors.newScheduledThreadPool(10);
ExecutorService executorService3=Executors.newSingleThreadExecutor();
```
### ThreadPoolExecutor的重要参数
```$xslt
 public ThreadPoolExecutor(int corePoolSize,//核心线程数
                              int maximumPoolSize,//最大线程数
                              long keepAliveTime,//线程执行和存活时间
                              TimeUnit unit,//线程执行和存活时间单位
                              BlockingQueue<Runnable> workQueue,//任务队列
                              RejectedExecutionHandler handler//队列满了,执行的拒绝策略) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             Executors.defaultThreadFactory(), handler);
    }
```
### ThreadPoolExecutor的执行策略
在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，若大于corePoolSize，则会将任务加入队列，若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，若线程数大于maximumPoolSize，则执行拒绝策略。或其他自定义方式（拒绝方式一般选择是记入日志,任务id，关键key,非高峰期重新批处理）。
若使用无界队列,则不存在任务进入队列失败的情况.这种情况下线程数达到corePoolSize就不会继续创建新线程,后续任务一直进入堆积，直到系统资源耗尽.


1、只保留1个 `pool.execute`方法 log信息如下
```$xslt
14:19:00.346 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 1， Thread 11
```

2.保留2个 `pool.execute`方法 log信息如下
```$xslt
14:16:19.579 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 1， Thread 11
14:16:24.587 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 2， Thread 11

//只有一个线程操作，中间有间隔时间等待task1完成.
```

3、保留5个 pool.execute方法 log信息如下:

```
14:09:52.977 [pool-1-thread-2] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 5， Thread 12
14:09:52.977 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 1， Thread 11
14:09:57.981 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 2， Thread 11
14:09:57.981 [pool-1-thread-2] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 3， Thread 12
14:10:02.983 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 4， Thread 11

//执行顺序解读
1.task1，2，3，4，5陆续添加到线程池中之行
2.线程中有一个核心线程,task1立即执行
3.task1执行过程中,2,3,4被放入队列中,但是由于队列容量只有3(ArrayBlockingQueue<Runnable>(3)),task5进入不了队列
4.线程池判断核心线程数<最大线程数,又新开了一个线程,立刻执行线程5
5.task1,5执行完成之后,ThreadPool存在2条线程依次执行任务2，3，4
```

4.保留6个 `pool.execute`方法 log信息如下
```$xslt
14:23:21.360 [main] INFO com.sunsun.threadpooldemo.handler.MyRejected - 自定义处理..
14:23:21.360 [pool-1-thread-2] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 5， Thread 12
14:23:21.362 [main] INFO com.sunsun.threadpooldemo.handler.MyRejected - 当前被拒绝任务为：6
14:23:21.360 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 1， Thread 11
14:23:26.363 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 3， Thread 11
14:23:26.363 [pool-1-thread-2] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 2， Thread 12
14:23:31.368 [pool-1-thread-2] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 4， Thread 12
```


5、保留6个 `pool.execute`方法 将构造方法中的队列替换成`new LinkedBlockingQueue<Runnable>()` log信息如下
```
14:13:51.024 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 1， Thread 11
14:13:56.032 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 2， Thread 11
14:14:01.033 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 3， Thread 11
14:14:06.037 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 4， Thread 11
14:14:11.041 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 5， Thread 11
14:14:16.046 [pool-1-thread-1] INFO com.sunsun.threadpooldemo.task.MyTask - run taskId = 6， Thread 11

//当队列换成了无界队列后,只初始化了一个线程,只有一个线程执行任务,maxsize这个参数失效
```

转自：
[ThreadPoolExecutor Demo示例](https://www.jianshu.com/p/94c8b2000ce4)
