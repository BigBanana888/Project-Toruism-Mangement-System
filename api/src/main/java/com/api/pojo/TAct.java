package com.api.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 活动表
 * </p>
 */
public class TAct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 标签
     */
    private String bq;

    /**
     * 介绍
     */
    private String js;
    private String imageUrl;

    /**
     * 限制人数
     */
    private Integer num;
//    预算
    private Integer ys;

    /**
     * 活动日期
     */
    private Date stime;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date modifiedTime;

    /**
     * 创建人id
     */
    private String createdId;

    /**
     * 更新人id
     */
    private String modifiedId;

    /**
     * 状态0待审核1已审核2未通过
     */
    private String ss;

    /**
     * 审核结果
     */
    private String rep;

    @TableField(exist = false)
    private String createdName;
    @TableField(exist = false)
    private String modifiedName;
    @TableField(exist = false)
    private Integer lys;
    @TableField(exist = false)
    private Integer yys;
    @TableField(exist = false)
    private String bqn;

    public String getBqn() {
        return bqn;
    }

    public void setBqn(String bqn) {
        this.bqn = bqn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getLys() {
        return lys;
    }

    public void setLys(Integer lys) {
        this.lys = lys;
    }

    public Integer getYys() {
        return yys;
    }

    public void setYys(Integer yys) {
        this.yys = yys;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBq() {
        return bq;
    }

    public void setBq(String bq) {
        this.bq = bq;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    public String getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(String modifiedId) {
        this.modifiedId = modifiedId;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return "TAct{" +
        "id=" + id +
        ", name=" + name +
        ", address=" + address +
        ", bq=" + bq +
        ", js=" + js +
        ", stime=" + stime +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        ", ss=" + ss +
        ", rep=" + rep +
        "}";
    }
}
