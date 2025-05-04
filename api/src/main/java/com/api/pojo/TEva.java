package com.api.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 评价表
 * </p>
 */
public class TEva implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String cid;

    /**
     * 评论内容
     */
    private String content;
    private Integer rate;

    /**
     * 评论图片
     */
    private String imageUrl;

    /**
     * 活动id
     */
    private String aid;

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
//    评价人名称
    @TableField(exist = false)
    private String aname;
    @TableField(exist = false)
    private String ah;

    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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
        return "TEva{" +
        "id=" + id +
        ", content=" + content +
        ", imageUrl=" + imageUrl +
        ", aid=" + aid +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        "}";
    }
}
