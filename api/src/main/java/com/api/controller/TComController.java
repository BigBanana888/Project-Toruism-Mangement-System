package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TCom;
import com.api.service.TComService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 帖子评论表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-com")
public class TComController {

    @Autowired
    private TComService tComService;
    @PostMapping("/list")
    public JsonResult list(@RequestBody TCom item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tComService.getList(item,currentPage,pageSize);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TCom item){
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setCreatedTime(new Date());
        boolean res = tComService.save(item);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TCom item){
        item.setModifiedTime(new Date());
        boolean res = tComService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TCom tCom = tComService.getById(id);
        System.out.println(tCom.toString());
        boolean res = tComService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
}

