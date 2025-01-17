package com.strelizia.arknights.service.impl;

import com.strelizia.arknights.dao.NickNameMapper;
import com.strelizia.arknights.dao.SkillDescMapper;
import com.strelizia.arknights.model.NickName;
import com.strelizia.arknights.model.SkillDesc;
import com.strelizia.arknights.service.SkillDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangzy
 * @Date 2021/4/2 17:54
 **/
@Service
public class SkillDescServiceImpl implements SkillDescService {

    @Autowired
    private SkillDescMapper skillDescMapper;

    @Autowired
    private NickNameMapper nickNameMapper;

    @Override
    public String getSkillDescByInfo(String[] infos) {
        Map<Integer, String> spType = new HashMap<>();
        Map<Integer, String> skillType = new HashMap<>();
        Map<String, String> levelStr = new HashMap<>();

        levelStr.put("专一", "8");
        levelStr.put("专二", "9");
        levelStr.put("专三", "10");

        spType.put(1, "自动回复");
        spType.put(2, "攻击回复");
        spType.put(4, "受击回复");
        spType.put(8, "被动");

        skillType.put(0, "被动");
        skillType.put(1, "手动触发");
        skillType.put(2, "自动触发");

        //默认只查询专精的技能描述
        if (infos[2] == null) {
            infos[2] = "8-10";
        }

        //干员名或者技能名
        String name = infos[1];

        String realName = nickNameMapper.selectNameByNickName(name);
        if (realName != null && !realName.equals(""))
            name = realName;

        //把等级字符串切分（可输入范围）
        String[] levels = infos[2].split("-");
        List<List<SkillDesc>> skillDesc = new ArrayList<>();
        //如果可以分解(既是输入的范围)
        if (levels.length == 2) {
            //先把专一专二专三转换成数字
            if (levelStr.containsKey(levels[0]))
                levels[0] = levelStr.get(levels[0]);
            if (levelStr.containsKey(levels[1]))
                levels[1] = levelStr.get(levels[1]);
            int min = Integer.parseInt(levels[0]);
            int max = Integer.parseInt(levels[1]);
            //取最大值最小值
            if (min > max) {
                int a = max;
                max = min;
                min = a;
            }
            //遍历插入结果集
            for (int i = min; i <= max; i++) {
                List<SkillDesc> descs = skillDescMapper.selectSkillDescByNameAndLevel(name, i);
                if (descs.size() > 0) {
                    skillDesc.add(descs);
                }
            }
            //不可分解(输入准确的等级)
        } else if (levels.length == 1) {
            if (levelStr.containsKey(infos[2]))
                infos[2] = levelStr.get(infos[2]);
            List<SkillDesc> descs = skillDescMapper.selectSkillDescByNameAndLevel(name, Integer.parseInt(infos[2]));
            if (descs.size() > 0) {
                skillDesc.add(descs);
            }
        }

        if (skillDesc.size() == 0) {
            return "未找到相应技能描述";
        }
        StringBuilder s = new StringBuilder(skillDesc.get(0).get(0).getOperatorName() + "：\n");
        for (List<SkillDesc> list : skillDesc) {
            for (SkillDesc sd : list) {
                s.append(sd.getSkillName()).append("level").append(sd.getSkillLevel()).append(":\n").append(sd.getSpInit()).append("/").append(sd.getSpCost()).append(" 持续").append(sd.getDuration()).append("秒 ").append(spType.get(sd.getSpType())).append("/").append(skillType.get(sd.getSkillType())).append(sd.getMaxCharge() == 1 ? "" : "最大充能" + sd.getMaxCharge()).append("\n").append(sd.getDescription()).append("\n\n");
            }
            s.append("\n");
        }

        return s.toString();
    }
}
