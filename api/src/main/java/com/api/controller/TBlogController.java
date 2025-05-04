package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TAct;
import com.api.pojo.TBlog;
import com.api.service.TBlogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 帖子表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-blog")
public class TBlogController {

    @Autowired
    private TBlogService tBlogService;
    @GetMapping("/one/{id}")
    public JsonResult one(@PathVariable String id){
        TBlog tBlog = tBlogService.getById(id);
        return JsonResult.success("Query success",tBlog);
    }
    @PostMapping("/list")
    public JsonResult list(@RequestBody TBlog item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tBlogService.getList(item,currentPage,pageSize);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TBlog item){
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setCreatedTime(new Date());
        boolean res = tBlogService.save(item);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TBlog item){
        item.setModifiedTime(new Date());
        boolean res = tBlogService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TBlog tBlog = tBlogService.getById(id);
        System.out.println(tBlog.toString());
        boolean res = tBlogService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
}

