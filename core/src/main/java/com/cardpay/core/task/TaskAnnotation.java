package com.cardpay.core.task;

import com.cardpay.basic.common.log.LogTemplate;
import com.cardpay.mgt.loan.service.PostLoanMonitorService;
import com.cardpay.mgt.user.model.UserToken;
import com.cardpay.mgt.user.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务(注解方式实现)
 *
 * @author rankai .
 */
@Component
public class TaskAnnotation {

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private PostLoanMonitorService postLoanMonitorService;

    /**
     * 每天凌晨3点执行清理过期USERTOKEN
     */
    @Scheduled(cron = "0 00 3 * * ?")
    public void clearToken() {
        LogTemplate.debug(this.getClass(), "message", "清理过期USERTOKEN开始");
        long startTime = new Date().getTime();
        List<UserToken> userTokens = userTokenService.selectAll();
        List<Integer> list = new ArrayList<>();
        for (UserToken userToken : userTokens) {
            long time = userToken.getExpirationTime().getTime();
            long nowTime = new Date().getTime();
            if (time - nowTime < 0) {

            }
        }
        long endTime = new Date().getTime();
        LogTemplate.debug(this.getClass(), "message", "清理过期USERTOKEN完成,耗时:" + (endTime - startTime) + "毫秒");
    }

    /**
     * 定时任务添加进件贷后监控实施
     */
    @Scheduled(cron = "0 00 1 * * ?")
    public void scanLoanByApplication(){
        LogTemplate.debug(this.getClass(), "message", "定时任务添加进件贷后监控实施开始");
        long startTime = new Date().getTime();
        postLoanMonitorService.scanLoanByApplication();
        long endTime = new Date().getTime();
        LogTemplate.debug(this.getClass(), "message", "定时任务添加进件贷后监控实施完成,耗时:" + (endTime - startTime) + "毫秒");
    }
}
