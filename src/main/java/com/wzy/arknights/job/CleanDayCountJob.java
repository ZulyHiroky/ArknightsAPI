package com.wzy.arknights.job;

import com.wzy.arknights.dao.UserFoundMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wangzy
 * @Date 2020/12/8 15:53
 **/

@Component
@Slf4j
public class CleanDayCountJob {

    @Autowired
    private UserFoundMapper userFoundMapper;

    //每天凌晨四点重置抽卡次数
    @Scheduled(cron = "${scheduled.cleanJob}")
    @Async
    public void cleanDayCountJob(){
        userFoundMapper.cleanTodayCount();
    }

}
