package com.api.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 沟通表
 * </p>
 */
public class TChat implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 接收人id
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
     * 发送人id
     */
    private String createdId;

    /**
     * 更新人id
     */
    private String modifiedId;

    /**
     * 0未查看1已查看
     */
    private String ss;

    @TableField(exist = false)
    private String createdName;
    @TableField(exist = false)
    private String ch;
    @TableField(exist = false)
    private String modifiedName;
//    接收方名称
    @TableField(exist = false)
    private String aname;
//    接收方头像
    @TableField(exist = false)
    private String ah;

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
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

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    @Override
    public String toString() {
        return "TChat{" +
        "id=" + id +
        ", content=" + content +
        ", aid=" + aid +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        ", ss=" + ss +
        "}";
    }
}
