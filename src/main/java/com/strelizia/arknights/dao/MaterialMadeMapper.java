package com.strelizia.arknights.dao;

import com.strelizia.arknights.model.MapCostInfo;
import com.strelizia.arknights.model.MapMatrixInfo;
import com.strelizia.arknights.model.MaterialInfo;
import com.strelizia.arknights.model.SourcePlace;

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

    //根据章节名查询地图列表
    List<MapCostInfo> selectMapByZone(String zoneName);

    //查询地图掉落材料
    List<MapMatrixInfo> selectMatrixByMap(String MapId);
}
