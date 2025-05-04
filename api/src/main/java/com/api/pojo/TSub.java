package com.api.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 报名表
 * </p>
 */
public class TSub implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 预约申请人
     */
    private String uid;

    /**
     * 申请内容
     */
    private String remark;

    /**
     * 活动id
     */
    private String aid;

    /**
     * 开始时间
     */
    private Date startedTime;

    /**
     * 状态0已报名1已取消
     */
    private String status;

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
    @TableField(exist = false)
    private String createdName;
    @TableField(exist = false)
    private String modifiedName;
    @TableField(exist = false)
    private String aname;
    @TableField(exist = false)
    private String address;
    @TableField(exist = false)
    private Date stime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
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

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "TSub{" +
        "id=" + id +
        ", uid=" + uid +
        ", remark=" + remark +
        ", aid=" + aid +
        ", startedTime=" + startedTime +
        ", status=" + status +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        "}";
    }
}
