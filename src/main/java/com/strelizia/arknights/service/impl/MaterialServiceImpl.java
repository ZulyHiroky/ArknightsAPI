package com.strelizia.arknights.service.impl;

import com.strelizia.arknights.dao.MaterialMadeMapper;
import com.strelizia.arknights.dao.OperatorEvolveMapper;
import com.strelizia.arknights.dao.SkillMateryMapper;
import com.strelizia.arknights.model.*;
import com.strelizia.arknights.service.MaterialService;
import com.strelizia.arknights.util.DescriptionTransformationUtil;
import com.strelizia.arknights.util.ImageUtil;
import com.strelizia.arknights.util.SendMsgUtil;
import com.strelizia.arknights.util.TextToImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import static com.strelizia.arknights.util.ImageUtil.replaceEnter;

/**
 * @author wangzy
 * @Date 2020/12/14 11:43
 **/
@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private OperatorEvolveMapper operatorEvolveMapper;

    @Autowired
    private SkillMateryMapper skillMateryMapper;

    @Autowired
    private MaterialMadeMapper materialMadeMapper;

    @Autowired
    private SendMsgUtil sendMsgUtil;

    @Override
    public String ZhuanJingCaiLiao(Long groupId, String[] args) {
        List<MaterialInfo> materialInfos;

        String skillName = "";
        Integer level = 0;
        if (args.length == 4){
            //四个参数就是##专精材料-干员—第几技能-专精等级
            Integer index = DescriptionTransformationUtil.ChangeStringToInt(args[2]);
            level = DescriptionTransformationUtil.ChangeStringToInt(args[3]);
            skillName = skillMateryMapper.selectSkillNameByAgentIndex(args[1],index);
            materialInfos = skillMateryMapper.selectSkillUpByAgentAndIndex(args[1], index, level);
        }else if (args.length == 3){
            //三个参数就是##专精材料-技能名-专精等级
            skillName = args[1];
            level = DescriptionTransformationUtil.ChangeStringToInt(args[2]);
            materialInfos = skillMateryMapper.selectSkillUpBySkillName(args[1], level);
        }else {
            materialInfos = null;
        }

        if (materialInfos ==null || materialInfos.size() == 0){
            return "找不到查询的内容";
        }else {
            sendImageWithPicAndStr(groupId, materialInfos, skillName + " 专精" + level + " 材料为：");
        }

        return "";
    }

    @Override
    public String JingYingHuaCaiLiao(Long groupId, String agent, Integer level) {
        List<MaterialInfo> materialInfos = operatorEvolveMapper.selectOperatorEvolveByName(agent, level);
        if (materialInfos.size() == 0){
            return "找不到查询的材料";
        }else {
            sendImageWithPicAndStr(groupId, materialInfos, agent + "干员 精英" + level + " 需要的材料为：");
        }
        return "";
    }

    @Override
    public String HeChengLuXian(Long groupId, String name) {
        List<MaterialInfo> materialInfos = materialMadeMapper.selectMadeMater(name);
        StringBuilder s = new StringBuilder(name + "的合成路线为：");
        if (materialInfos.size() == 0){
            s = new StringBuilder("找不到该材料的合成路线");
        }else {
            sendImageWithPicAndStr(groupId, materialInfos, name + "的合成路线为：");
        }
        return "";
    }

    @Override
    public String HuoQuTuJing(String name) {
        StringBuilder s;
        if (!name.endsWith("-all")) {
            List<SourcePlace> sourcePlaces = materialMadeMapper.selectMaterSource(name);
            s = new StringBuilder(name + "的主线关卡掉率排行前十为：");
            if (sourcePlaces.size() == 0) {
                s = new StringBuilder("找不到该材料的获取关卡");
            } else {
                for (SourcePlace p : sourcePlaces) {
                    String zoneName = p.getZone_name();
                    String code = p.getCode();
                    Double rate = p.getRate();
                    s.append("\n关卡名称：").append(zoneName).append("\t").append(code).append("\t掉落概率:").append(rate).append("%");
                }
            }
            s.append("\n如需查看活动关卡，请在材料名后面加-all，中间无空格");
        }else {
            name = name.replace("-all","");
            List<SourcePlace> sourcePlaces = materialMadeMapper.selectMaterSourceAllStage(name);
            s = new StringBuilder(name + "的全部关卡（包含活动关卡）掉率排行前十为：");
            if (sourcePlaces.size() == 0) {
                s = new StringBuilder("找不到该材料的获取关卡");
            } else {
                for (SourcePlace p : sourcePlaces) {
                    String zoneName = p.getZone_name();
                    String code = p.getCode();
                    Double rate = p.getRate();
                    s.append("\n关卡名称：").append(zoneName).append("\t").append(code).append("\t掉落概率:").append(rate).append("%");
                }
            }
        }
        return s.toString();
    }

    @Override
    public String selectAgentData(String name) {
        OperatorData operatorData = operatorEvolveMapper.selectOperatorData(name);
        String s = "未找到该干员数据";
        if (operatorData.getAtk() != null) {
            s = name + "满精英化满级，无信赖无潜能面板为：" +
                    "\n生命上限：" + operatorData.getMaxHp() + "\t攻击：" + operatorData.getAtk() +
                    "\n防御：" + operatorData.getDef() + "\t法术抵抗：" + operatorData.getMagicResistance() +
                    "\n部署费用：" + operatorData.getCost() + "\t阻挡数：" + operatorData.getBlockCnt() +
                    "\n攻击间隔：" + operatorData.getBaseAttackTime() + "s\t再部署：" + operatorData.getRespawnTime() + "s";
        }
        return s;
    }

    @Override
    public String selectMaterByMap(Long groupId, String MapId) {
        List<MapMatrixInfo> mapMatrixInfos = materialMadeMapper.selectMatrixByMap(MapId);

        if (mapMatrixInfos.size() == 0){
            return "没有找到该地图掉落的材料";
        }else {
            int height = 100 * mapMatrixInfos.size() + 120;
            BufferedImage image = new BufferedImage(3000, height,
                    BufferedImage.TYPE_INT_BGR);//创建图片画布
            Graphics g = image.getGraphics();
            g.setColor(Color.WHITE); // 先用白色填充整张图片,也就是背景
            g.fillRect(0, 0, 3000, height);//画出矩形区域，以便于在矩形区域内写入文字
            g.setColor(Color.black);// 再换成黑色，以便于写入文字
            g.setFont(new Font("楷体", Font.PLAIN, 100));// 设置画笔字体
            g.drawString(MapId + "掉落的材料列表为：", 0, 100);
            for (int i = 0; i < mapMatrixInfos.size(); i++) {
                MapMatrixInfo matrix = mapMatrixInfos.get(i);
                String imgBase64 = materialMadeMapper.selectMaterialPicByName(matrix.getMaterial_name());
                g.drawImage(ImageUtil.Base64ToImageBuffer(imgBase64), 0, (i + 1) * 100, 100, 100, null);// 画出材料图标
                g.drawString(matrix.getMaterial_name() + "\t掉率：" + matrix.getRate() + "%\t测试次数：" + matrix.getTimes() + "\t掉落个数：" + matrix.getQuantity(), 100, (i + 2) * 100);
            }
            g.dispose();
            sendMsgUtil.CallOPQApiSendImg(groupId, null, SendMsgUtil.picBase64Buf,
                    replaceEnter(new BASE64Encoder().encode(TextToImage.imageToBytes(image))),2);
        }
        return "";
    }

    @Override
    public String selectMapList(String zoneName) {
        List<MapCostInfo> mapCostInfos = materialMadeMapper.selectMapByZone(zoneName);
        StringBuilder s = new StringBuilder("地图ID以及理智花费为：");

        for (MapCostInfo mapInfo:mapCostInfos){
            s.append("\n").append(mapInfo.getZoneName()).append("\t地图ID：").append(mapInfo.getCode()).append("\t理智消耗：").append(mapInfo.getApCost());
        }
        return s.toString();
    }

    @Override
    public String selectZoneList() {
        List<String> zones = materialMadeMapper.selectAllZone();
        StringBuilder s = new StringBuilder("当前所有章节列表：");
        for (String zone: zones){
            s.append("\n").append(zone);
        }
        return s.toString();
    }

    /**
     * 材料结果转图片发送
     * @param groupId 发送群号
     * @param materialInfos 材料列表
     * @param s 描述文字
     */
    public void sendImageWithPicAndStr(Long groupId, List<MaterialInfo> materialInfos, String s){
        int height = 100 * materialInfos.size() + 120;
        BufferedImage image = new BufferedImage(s.length() * 100, height,
                BufferedImage.TYPE_INT_BGR);//创建图片画布
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE); // 先用白色填充整张图片,也就是背景
        g.fillRect(0, 0, s.length() * 100, height);//画出矩形区域，以便于在矩形区域内写入文字
        g.setColor(Color.black);// 再换成黑色，以便于写入文字
        g.setFont(new Font("楷体", Font.PLAIN, 100));// 设置画笔字体
        g.drawString(s, 0, 100);
        for (int i = 0; i < materialInfos.size(); i++){
            MaterialInfo m = materialInfos.get(i);
            String imgBase64 = materialMadeMapper.selectMaterialPicByName(m.getMaterialName());
            g.drawImage(ImageUtil.Base64ToImageBuffer(imgBase64), 0, (i + 1) * 100, 100, 100, null);// 画出材料图标
            g.drawString(m.getMaterialName() + "*" + m.getMaterialNum() + "个", 100, (i + 2) * 100);
        }
        g.dispose();
        sendMsgUtil.CallOPQApiSendImg(groupId, null, SendMsgUtil.picBase64Buf,
                replaceEnter(new BASE64Encoder().encode(TextToImage.imageToBytes(image))),2);
    }
}
