package com.atguigu.crm.test;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

public class QuartzTest2 {

	public static void main(String[] args) throws SchedulerException, ParseException {
		// 实现 Job 接口，可使 Java 类变为可调度的任务
		// 创建描述 Job 的 JobDetail 对象
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setJobClass(MyJob.class);
		jobDetail.setName("myjobd");
		
		// 创建 CronTrigger 对象
		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setName("trigger");
		
		// 设置触发 Job 执行的时间规则
		trigger.setCronExpression("0/3 29 14 13 10 ? 2015");
		
		// 通过 SchedulerFactory 获取 Scheduler 对象
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		
		// 向 SchedulerFactory 中注册 JobDetail 和 Trigger
		scheduler.scheduleJob(jobDetail, trigger);
		
		// 启动调度任务
		scheduler.start();
	}

}
