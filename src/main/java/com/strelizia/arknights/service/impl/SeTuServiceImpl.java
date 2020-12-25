package com.strelizia.arknights.service.impl;

import com.strelizia.arknights.dao.AdminUserMapper;
import com.strelizia.arknights.dao.SeTuMapper;
import com.strelizia.arknights.model.ImgUrlInfo;
import com.strelizia.arknights.service.SeTuService;
import com.strelizia.arknights.util.ImageUtil;
import com.strelizia.arknights.util.SendMsgUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author wangzy
 * @Date 2020/12/23 16:53
 **/

@Service
public class SeTuServiceImpl implements SeTuService {

    @Autowired
    private SeTuMapper seTuMapper;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private SendMsgUtil sendMsgUtil;

    @Resource(name="taskModuleExecutor")
    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @Autowired
    private ImageUtil imageUtil;

    @Value("${pixiv.count}")
    private Integer count;

    @Override
    public String getImageIntoDb(String json, Integer type, String name) {
        JSONObject jsonObj = new JSONObject(json);
        JSONArray array = new JSONArray(jsonObj.get("GroupPic").toString());
        String url = array.getJSONObject(0).getString("Url");
        poolTaskExecutor.execute(() -> {
            String base64 = imageUtil.getImageBase64ByUrl(url);
            seTuMapper.insertSeTuUrl(base64, type);
        });
        return "感谢[" + name + "]的涩图";
    }

    @Override
    public String PrivategetImageIntoDb(String json, Integer type) {
        JSONObject jsonObj = new JSONObject(json);
        JSONArray array = new JSONArray(jsonObj.get("FriendPic").toString());
        String url = array.getJSONObject(0).getString("Url");
        poolTaskExecutor.execute(() -> {
            String base64 = imageUtil.getImageBase64ByUrl(url);
            seTuMapper.insertSeTuUrl(base64, type);
        });
        return "涩图已收到get√";
    }

    @Override
    public String sendImageByType(Long qq, Long groupId, Integer type, String name) {
        String qqMd5 = DigestUtils.md5DigestAsHex(qq.toString().getBytes());
        Integer pixiv = seTuMapper.selectTodaySeTuByQQ(qqMd5);
        boolean b = ifAdminUser(qqMd5);
        if (pixiv < count || b) {
            List<ImgUrlInfo> imgs = seTuMapper.selectSeTuUrl(type);
            if (imgs == null){
                return "没有找到涩图哦";
            }else {
                //随机一张图片
                String urls = imgs.get(new Random().nextInt(imgs.size())).getUrl();

                sendMsgUtil.CallOPQApiSendImg(groupId, null,SendMsgUtil.picBase64Buf, urls,2);
                //更新请求涩图数量
                seTuMapper.updateTodaySeTu(qqMd5,name,groupId);
                //空字符串不返回文字信息
                return "";
            }
        }else {
            return name + "别冲了，一天就"+ count +"张涩图";
        }
    }

    @Override
    public Integer getAllImageIntoLocal(String dir) {
        List<ImgUrlInfo> imgUrlInfos = seTuMapper.selectSeTuUrl(1);
        int i = 0;
        for (ImgUrlInfo img:imgUrlInfos) {
            imageUtil.getImgToLocal(dir, img.getId(),img.getUrl());
            i++;
        }
        return i;
    }

    public boolean ifAdminUser(String qq){
        //管理员账户无限涩图
        List<String> admins = adminUserMapper.selectAllAdmin();
        for (String admin:admins){
            if (admin.equals(qq)){
                return true;
            }
        }
        return false;
    }
}
