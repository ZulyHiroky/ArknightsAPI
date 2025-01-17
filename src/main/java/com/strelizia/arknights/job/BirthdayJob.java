package com.strelizia.arknights.job;

import com.strelizia.arknights.dao.OperatorInfoMapper;
import com.strelizia.arknights.dao.UserFoundMapper;
import com.strelizia.arknights.model.OperatorInfo;
import com.strelizia.arknights.service.DailyCountService;
import com.strelizia.arknights.service.OperatorInfoService;
import com.strelizia.arknights.util.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangzy
 * @Date 2020/12/16 14:10
 **/
@Component
@Slf4j
public class BirthdayJob {
    @Autowired
    private OperatorInfoMapper operatorInfoMapper;

    @Autowired
    private UserFoundMapper userFoundMapper;

    @Autowired
    private SendMsgUtil sendMsgUtil;

    //每天晚上8点发送当日统计结果
    @Scheduled(cron = "${scheduled.birthdayJob}")
    @Async
    public void birthdayJob() {
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        Date date = new Date();

        String monthStr = month.format(date);
        String dayStr = day.format(date);
        if (monthStr.startsWith("0")) {
            monthStr = monthStr.substring(1);
        }
        if (dayStr.startsWith("0")) {
            dayStr = dayStr.substring(1);
        }
        String today = monthStr + "月" + dayStr + "日";

        StringBuilder s = new StringBuilder("今天是" + today + "祝 ");
        List<String> operatorByBirthday = operatorInfoMapper.getOperatorByBirthday(today);
        if (operatorByBirthday != null && operatorByBirthday.size() > 0) {
            //今日有干员过生日才推送
            for (String name : operatorByBirthday) {
                s.append(name).append(" ");
            }
            s.append("干员生日快乐");
            List<Long> groups = userFoundMapper.selectAllGroups();
            for (Long groupId : groups) {
                sendMsgUtil.CallOPQApiSendMsg(groupId, s.toString(), 2);
            }
            log.info("{}每日干员生日推送发送成功", new Date());
        }
    }
}
