package com.api.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 类型
 * </p>
 */
public class TType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "TType{" +
        "id=" + id +
        ", name=" + name +
        ", remark=" + remark +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        "}";
    }
}
