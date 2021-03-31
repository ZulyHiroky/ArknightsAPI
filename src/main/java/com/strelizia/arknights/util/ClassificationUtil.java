package com.strelizia.arknights.util;

import com.strelizia.arknights.model.ClassificationEnum;

import java.util.HashMap;
import java.util.Map;

import static com.strelizia.arknights.model.ClassificationEnum.*;

/**
 * @author wangzy
 * @Date 2020/12/22 11:36
 **/
public class ClassificationUtil {
    public static ClassificationEnum GetClass(String s){
        Map<String,ClassificationEnum> map = new HashMap<>();
        map.put("菜单",CaiDan);

        map.put("菜单详情",XiangXiCaiDan);
        map.put("详细菜单",XiangXiCaiDan);

        map.put("抽卡",ChouKa);
        map.put("单抽",ChouKa);
        map.put("寻访",ChouKa);

        map.put("十连寻访",ShiLian);
        map.put("十抽",ShiLian);
        map.put("十连",ShiLian);

        map.put("卡池",KaChi);
        map.put("卡池列表",KaChi);

        map.put("卡池干员",KaChiQingdan);
        map.put("卡池up",KaChiQingdan);
        map.put("干员up",KaChiQingdan);
        map.put("卡池清单",KaChiQingdan);

        map.put("垫刀查询",DianDaoChaXun);
        map.put("垫刀",DianDaoChaXun);
        map.put("我的垫刀",DianDaoChaXun);

        map.put("专精材料",ZhuanJingCaiLiao);
        map.put("专精",ZhuanJingCaiLiao);
        map.put("技能专精",ZhuanJingCaiLiao);

        map.put("精一材料",JingYiCaiLiao);
        map.put("精英一材料",JingYiCaiLiao);

        map.put("精二材料",JingErCaiLiao);
        map.put("精英二材料",JingErCaiLiao);

        map.put("合成路线",HeChengLuXian);
        map.put("材料合成",HeChengLuXian);

        map.put("获取途径",CaiLiaoHuoQu);
        map.put("材料关卡",CaiLiaoHuoQu);
        map.put("材料获取",CaiLiaoHuoQu);
        map.put("材料掉落",CaiLiaoHuoQu);

        map.put("公招截图",GongZhaoJieTu);
        map.put("公开招募",GongKaiZhaoMu);

        map.put("涩图",SeTu);
        map.put("不够涩",SeTu);

        map.put("给你涩图",GeiNiSeTu);
        map.put("上传涩图",GeiNiSeTu);

        map.put("服务器查询",XiTongXinXi);
        map.put("系统信息",XiTongXinXi);
        map.put("服务器信息",XiTongXinXi);

        map.put("干员面板",GanYuanMianBan);
        map.put("面板信息",GanYuanMianBan);
        map.put("面板",GanYuanMianBan);

        map.put("地图掉落",DiTuDiaoLuo);
        map.put("地图材料",DiTuDiaoLuo);
        map.put("关卡掉落",DiTuDiaoLuo);
        map.put("关卡材料",DiTuDiaoLuo);

        map.put("地图列表",DiTuLieBiao);
        map.put("全部地图",DiTuLieBiao);

        map.put("章节列表",ZhangJieLieBiao);
        map.put("全部章节",ZhangJieLieBiao);

        map.put("动态查询",DongTaiChaXun);
        map.put("查询动态",DongTaiChaXun);
        map.put("B站动态",DongTaiChaXun);
        map.put("b站动态",DongTaiChaXun);

        map.put("最新动态",ZuiXinDongTai);

        map.put("关注列表",GuanZhuLieBiao);

        map.put("最新投稿",ZuiXinTouGao);
        map.put("最新视频",ZuiXinTouGao);

        map.put("敌人信息",DiRenXinXi);
        map.put("敌人查询",DiRenXinXi);
        map.put("查询敌人",DiRenXinXi);

        map.put("敌人全名",DiRenQuanMing);
        map.put("敌人名称",DiRenQuanMing);
        map.put("敌人名字",DiRenQuanMing);
        map.put("敌人清单",DiRenQuanMing);
        map.put("敌人列表",DiRenQuanMing);

        map.put("sql",SQL);
        map.put("SQL",SQL);

        map.put("所有微博",WeiBoLieBiao);
        map.put("微博列表",WeiBoLieBiao);

        map.put("微博正文",WeiBoZhengWen);

        map.put("干员列表",GanYuanLieBiao);
        map.put("搜索干员",GanYuanLieBiao);
        map.put("查找干员",GanYuanLieBiao);
        map.put("干员查询",GanYuanLieBiao);

        map.put("干员档案",GanYuanDangAn);
        map.put("详细档案",GanYuanDangAn);

        map.put("声优列表",ShengYouLieBiao);
        map.put("声优查询",ShengYouLieBiao);
        map.put("查询声优",ShengYouLieBiao);
        map.put("声优",ShengYouLieBiao);
        map.put("CV",ShengYouLieBiao);

        map.put("画师查询",HuaShiLieBiao);
        map.put("查询画师",HuaShiLieBiao);
        map.put("画师列表",HuaShiLieBiao);

        map.put("消息群发",QunFaXiaoXi);
        map.put("群发消息",QunFaXiaoXi);

        map.put("基建技能",JiJianJiNeng);

        return map.get(s);
    }
}
