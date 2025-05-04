package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TNotice;
import com.api.service.TNoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * Notice表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-notice")
public class TNoticeController {

    @Autowired
    private TNoticeService tNoticeService;
    @PostMapping("/list")
    public JsonResult list(@RequestBody TNotice item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tNoticeService.getList(item,currentPage,pageSize);
        return JsonResult.success("Select Success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TNotice item){
        QueryWrapper<TNotice> q = new QueryWrapper<>();
        q.eq(!Objects.isNull(item.getName()),"name",item.getName());
        TNotice one = tNoticeService.getOne(q);
        if(!Objects.isNull(one)){
            return JsonResult.fail(202,"Insert Fail，名称已存在");
        }
//        String id = UUID.randomUUID().toString()
//                .replace("-", "");
//        item.setId(id);
        item.setCreatedTime(new Date());
        boolean res = tNoticeService.save(item);
        if(res){
            return JsonResult.success("Insert Success");
        }else{
            return JsonResult.fail(203,"Insert Fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TNotice item){
        QueryWrapper<TNotice> q = new QueryWrapper<>();
        q.eq(!Objects.isNull(item.getName()),"name",item.getName());
        q.ne(!Objects.isNull(item.getId()),"id",item.getId());
        TNotice one = tNoticeService.getOne(q);
        if(!Objects.isNull(one)){
            return JsonResult.fail(203,"Update Fail");
        }
        item.setModifiedTime(new Date());
        boolean res = tNoticeService.updateById(item);
        if(res){
            return JsonResult.success("Update Success");
        }else{
            return JsonResult.fail(203,"Update Fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        boolean res = tNoticeService.removeById(id);
        if(res){
            return JsonResult.success("Delete Success");
        }else{
            return JsonResult.fail(204,"Delete Fail");
        }
    }
}

