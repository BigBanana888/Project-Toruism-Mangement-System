package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TUser;
import com.api.service.TUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-user")
public class TUserController {

    @Autowired
    private TUserService tUserService;

    @PostMapping("/login-email")
    public JsonResult loginByEmail(@RequestBody TUser user) {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq(!Objects.isNull(user.getEmail())&&!"".equals(user.getUsername()),"email",user.getEmail());
        TUser tUser = tUserService.getOne(wrapper);
        if(Objects.isNull(tUser)){
            return JsonResult.fail(202,"Email not exits");
        }
        if("pending".equals(tUser.getSs())){
            return JsonResult.fail(203,"Pending certification");
        }
        String md5Pwd = DigestUtils.md5DigestAsHex(tUser.getId().concat(user.getPassword()).getBytes());
        if(!md5Pwd.equals(tUser.getPassword())){
            return JsonResult.fail(204,"Incorrect password");
        }
        return JsonResult.success("Query success",tUser);
    }
    @PostMapping("/login-username")
    public JsonResult loginByPwd(@RequestBody TUser user){
        System.out.println(user.getUsername());
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq(!Objects.isNull(user.getUsername())&&!"".equals(user.getUsername()),"username",user.getUsername());
        TUser tUser = tUserService.getOne(wrapper);
        if(Objects.isNull(tUser)){
            return JsonResult.fail(202,"student number not exits.");
        }
        if("pending".equals(tUser.getSs())){
            return JsonResult.fail(203,"Pending certification");
        }
        String md5Pwd = DigestUtils.md5DigestAsHex(tUser.getId().concat(user.getPassword()).getBytes());
        if(!md5Pwd.equals(tUser.getPassword())){
            return JsonResult.fail(202,"Incorrect password");
        }
        return JsonResult.success("Query success",tUser);
    }

    @PostMapping("/list")
    public JsonResult list(@RequestBody TUser user, @RequestParam int currentPage, @RequestParam int pageSize){
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.like(!Objects.isNull(user.getUsername())&&!"".equals(user.getUsername()),
                "username",user.getUsername());
        wrapper.like(!Objects.isNull(user.getPhone())&&!"".equals(user.getPhone()),
                "phone",user.getPhone());
        wrapper.like(!Objects.isNull(user.getRoleId())&&!"".equals(user.getRoleId()),
                "role_id",user.getRoleId());
        wrapper.orderByDesc("created_time");
        Page<TUser> res = tUserService.page(new Page<>(currentPage,pageSize),wrapper);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TUser user) throws Exception {
        QueryWrapper<TUser> q = new QueryWrapper<>();
        q.eq(!Objects.isNull(user.getUsername()),"username",user.getUsername());
        TUser one = tUserService.getOne(q);
        if(!Objects.isNull(one)){
            return JsonResult.fail(202,"Failed to add, the account already exists");
        }
        q.clear();
        q.eq(!Objects.isNull(user.getUsername()),"email",user.getEmail());
        TUser one1 = tUserService.getOne(q);
        if(!Objects.isNull(one1)){
            return JsonResult.fail(202,"Failed to add, the email already exists");
        }
        q.clear();
        q.eq(!Objects.isNull(user.getEmail()),"email",user.getEmail());
        one = tUserService.getOne(q);
        if(!Objects.isNull(one)){
            return JsonResult.fail(202,"Failed to add, the email already exists");
        }
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        user.setId(id);
        user.setRoleId("2");
        user.setNum(0);
        user.setSs("pending");
        user.setRate(0);
        user.setBirth(new Date());
        user.setCreatedTime(new Date());
        String md5Pwd = DigestUtils.md5DigestAsHex(id.concat(user.getPassword()).getBytes());
        user.setPassword(md5Pwd);
        boolean res = tUserService.save(user);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/updatePwd")
    public JsonResult updatePwd(@RequestBody TUser user){
        System.out.println("user id is "+ user.getId());
        System.out.println("original password is "+ user.getPassword());
        System.out.println("concat str is "+ user.getId().concat(user.getPassword()).getBytes());
        String md5Pwd = DigestUtils.md5DigestAsHex(user.getId().concat(user.getPassword()).getBytes());

        System.out.println("encry pwd id is "+ md5Pwd);
        user.setPassword(md5Pwd);
        boolean res = tUserService.updateById(user);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TUser user){
        System.out.println(user.getRoleId());
        QueryWrapper<TUser> q = new QueryWrapper<>();
        q.eq(!Objects.isNull(user.getUsername()),"username",user.getUsername());
        q.ne(!Objects.isNull(user.getId()),"id",user.getId());
        TUser one = tUserService.getOne(q);
        if(!Objects.isNull(one)){
            return JsonResult.fail(203,"Update fail");
        }
        user.setModifiedTime(new Date());
        boolean res = tUserService.updateById(user);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        boolean res = tUserService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
    @GetMapping("/one/{id}")
    public JsonResult one(@PathVariable String id){
            return JsonResult.success("",tUserService.getById(id));
    }
}

