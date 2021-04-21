package com.mq.job;

import com.mq.service.ProfitSharingService;
import com.mq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ProfitSharingJob {

	protected static final Logger logger = LoggerFactory.getLogger(ProfitSharingJob.class);

	@Resource
	private ProfitSharingService profitSharingService;

	@Scheduled(cron = "0 0 1 * * ?")
	public void execute() {
		Date yesterday = DateUtil.addDay(new Date(), -1);
		String prefix = DateUtil.dateToString(yesterday);
		Date beginTime = DateUtil.stringToDate(prefix + " 00:00:00", DateUtil.DATE_TIME_FORMAT);
		Date endTime = DateUtil.stringToDate(prefix + " 23:59:59", DateUtil.DATE_TIME_FORMAT);
		logger.info("定时分账任务开始");
		profitSharingService.profitShare(beginTime, endTime);
		logger.info("定时分账任务结束");
	}
}
