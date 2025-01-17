package com.strelizia.arknights.dao;

import com.strelizia.arknights.model.MapCostInfo;
import com.strelizia.arknights.model.MapMatrixInfo;
import com.strelizia.arknights.model.MaterialInfo;
import com.strelizia.arknights.model.SourcePlace;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangzy
 * @Date 2020/12/14 15:05
 **/
public interface MaterialMadeMapper {

    //根据材料名获取该材料的合成列表
    List<MaterialInfo> selectMadeMater(String name);

    //根据材料名获取材料获取的主线关卡列表
    List<SourcePlace> selectMaterSource(String name);

    //根据材料名获取材料获取的所有关卡列表
    List<SourcePlace> selectMaterSourceAllStage(String name);

    //查询所有章节
    List<String> selectAllZone();

    //查询所有地图
    List<MapCostInfo> selectAllMap();

    //查询所有章节Id
    List<String> selectAllZoneId();

    //查询所有地图Id
    List<String> selectAllMapId();

    //查询地图消耗理智
    Integer selectStageCost(String MapId);

    //根据章节名查询地图列表
    List<MapCostInfo> selectMapByZone(String zoneName);

    //查询地图掉落材料
    List<MapMatrixInfo> selectMatrixByMap(String MapId);

    //查询材料图标base64
    String selectMaterialPicByName(String name);

    String selectMaterialPicById(Integer id);

    //查询全部材料id
    List<Integer> selectAllMaterId();

    //更新材料图标
    Integer updateBase64ById(@Param("base64") String base64, @Param("id") Integer id);
}
