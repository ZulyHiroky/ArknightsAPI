package com.strelizia.arknights.model;

/**
 * @author wangzy
 * @Date 2021/4/7 17:14
 **/
public class SkinInfo {
    private Integer operatorId;
    private String operatorName;
    private String skinGroupName;
    private String skinName;
    private String skinBase64;
    private String dialog;
    private String drawerName;

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSkinGroupName() {
        return skinGroupName;
    }

    public void setSkinGroupName(String skinGroupName) {
        this.skinGroupName = skinGroupName;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public String getSkinBase64() {
        return skinBase64;
    }

    public void setSkinBase64(String skinBase64) {
        this.skinBase64 = skinBase64;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }
}
