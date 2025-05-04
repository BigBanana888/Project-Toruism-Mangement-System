package com.api.pojo;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 */
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识id
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 学号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 兴趣
     */
    private String xq;

    /**
     * 介绍
     */
    private String js;

    /**
     * 头像路径
     */
    private String imageUrl;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birth;

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
     * 已发布活动次数
     */
    private Integer num;

    /**
     * 当前评分*10
     */
    private Integer rate;

    /**
     * 身份证明
     */
    private String sf;

    /**
     * 审核状态0未审核1已审核2不通过
     */
    private String ss;

    /**
     * 原因说明
     */
    private String rep;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
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
        return "TUser{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", username=" + username +
        ", password=" + password +
        ", xq=" + xq +
        ", js=" + js +
        ", imageUrl=" + imageUrl +
        ", phone=" + phone +
        ", email=" + email +
        ", sex=" + sex +
        ", birth=" + birth +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", createdId=" + createdId +
        ", modifiedId=" + modifiedId +
        ", num=" + num +
        ", rate=" + rate +
        ", sf=" + sf +
        ", ss=" + ss +
        ", rep=" + rep +
        "}";
    }
}
