package com.mq.job;

import com.mq.service.ProfitSharingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProfitSharingJob {

	protected static final Logger logger = LoggerFactory.getLogger(ProfitSharingJob.class);

	@Resource
	private ProfitSharingService profitSharingService;

	@Scheduled(cron = "0 0 1 * * ?")
	public void execute() {
		logger.info("定时分账任务开始");
		profitSharingService.profitShare();
		logger.info("定时分账任务结束");
	}
}
