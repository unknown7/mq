package com.mq.job;

import com.mq.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AgeJob {

	protected static final Logger logger = LoggerFactory.getLogger(AgeJob.class);

	@Resource
	private EmployeeService employeeService;

	@Scheduled(cron = "0 0 0 * * ?")
	public void execute() {
		logger.info("定时计算员工年龄任务开始");
		employeeService.updateAge();
		logger.info("定时计算员工年龄任务结束");
	}
}
