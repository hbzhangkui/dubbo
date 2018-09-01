package com.jt.order.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;

public class PaymentOrderJob extends QuartzJobBean{
	
	//定义任务的执行
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//获取spring容器
		ApplicationContext applicationContext = 
				(ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
		
		//将超时的订单状态由1改为6
		OrderMapper orderMapper = 
				applicationContext.getBean(OrderMapper.class);
		
		/**
		 * sql:update tb_order set status = 6 
		 * 	where status = 1 and created < new -2天
		 * 规定2天超时
		 */
		Date dateAgo = new DateTime().minusDays(2).toDate();
		orderMapper.updateStatus(dateAgo);
		System.out.println("定时任务执行完成");
	}
	
	
}
