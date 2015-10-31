package com.atguigu.crm.test;

import java.util.Date;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

public class QuartzTest {

	public static void main(String[] args) throws SchedulerException {
		// 实现 Job 接口，可使 Java 类变为可调度的任务
		// 创建描述 Job 的 JobDetail 对象
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setJobClass(MyJob.class);
		jobDetail.setName("myjobd");
		
		// 创建 SimpleTrigger 对象
		SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
		simpleTrigger.setName("simpleTrigger");
		
		// 设置触发 Job 执行的时间规则
		simpleTrigger.setStartTime(new Date());
		simpleTrigger.setRepeatCount(10);
		simpleTrigger.setRepeatInterval(1000 * 3);
		
		// 通过 SchedulerFactory 获取 Scheduler 对象
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		
		// 向 SchedulerFactory 中注册 JobDetail 和 Trigger
		scheduler.scheduleJob(jobDetail, simpleTrigger);
		
		// 启动调度任务
		scheduler.start();
	}

}
