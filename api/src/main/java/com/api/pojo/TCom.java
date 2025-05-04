package com.api.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 帖子评论表
 * </p>
 */
public class TCom implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论图片
     */
    private String imageUrl;

    /**
     * 帖子id
     */
    private String bid;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建人id
     */
    private String createdId;
    /**
     * 更新时间
     */
    private Date modifiedTime;


    /**
     * 更新人id
     */
    private String modifiedId;


    @TableField(exist = false)
    private String createdName;
    @TableField(exist = false)
    private String modifiedName;
    @TableField(exist = false)
    private String bname;

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

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
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
        return "TCom{" +
        "id=" + id +
        ", content=" + content +
        ", imageUrl=" + imageUrl +
        ", bid=" + bid +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        "}";
    }
}
