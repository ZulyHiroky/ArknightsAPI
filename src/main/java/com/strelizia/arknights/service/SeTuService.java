package com.strelizia.arknights.service;

/**
 * @author wangzy
 * @Date 2020/12/23 16:51
 **/
public interface SeTuService {
    String getImageIntoDb(String url, Integer type, String name);

    String sendImageByType(Long qq, Long groupId, Integer type, String name);
}
