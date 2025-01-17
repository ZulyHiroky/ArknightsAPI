package com.strelizia.arknights.model;

/**
 * @author wangzy
 * @Date 2021/4/1 11:24
 **/
public class SkillDesc {
    private Integer skillId;
    private Integer skillType;
    private Integer spType;
    private Integer spCost;
    private Integer spInit;
    private Integer duration;
    private String description;
    private Integer skillLevel;
    private Integer maxCharge;
    private String skillName;
    private String operatorName;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getSkillType() {
        return skillType;
    }

    public void setSkillType(Integer skillType) {
        this.skillType = skillType;
    }

    public Integer getSpType() {
        return spType;
    }

    public void setSpType(Integer spType) {
        this.spType = spType;
    }

    public Integer getSpCost() {
        return spCost;
    }

    public void setSpCost(Integer spCost) {
        this.spCost = spCost;
    }

    public Integer getSpInit() {
        return spInit;
    }

    public void setSpInit(Integer spInit) {
        this.spInit = spInit;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Integer getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(Integer maxCharge) {
        this.maxCharge = maxCharge;
    }
}
