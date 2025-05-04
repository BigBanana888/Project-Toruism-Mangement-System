package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TAct;
import com.api.pojo.TUser;
import com.api.service.TActService;
import com.api.service.TUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-act")
public class TActController {

    @Autowired
    private TActService tActService;
    @Autowired
    private TUserService tUserService;
    @GetMapping("/one1")
    public JsonResult one1(){

        return JsonResult.success("Query success",tActService.getById("038b9379d8294d8192ae089f592f820d"));
    }
    @PostMapping("/list")
    public JsonResult list(@RequestBody TAct item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tActService.getList(item,currentPage,pageSize);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TAct item){
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setSs("pending");
        item.setYs(item.getYs()*100);
        item.setCreatedTime(new Date());
        TUser tUser = tUserService.getById(item.getCreatedId());
        System.out.println(tUser.getNum());
        tUser.setNum(tUser.getNum()+1);
        tUserService.updateById(tUser);
        boolean res = tActService.save(item);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TAct item){
        item.setModifiedTime(new Date());
        boolean res = tActService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TAct tAct = tActService.getById(id);
        System.out.println(tAct.toString());
        boolean res = tActService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
    @GetMapping("/one/{id}")
    public JsonResult one(@PathVariable String id){
        TAct tAct = tActService.getById(id);
        return JsonResult.success("Query success",tAct);
    }
}

