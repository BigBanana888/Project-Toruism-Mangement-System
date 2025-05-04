package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TSub;
import com.api.service.TSubService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 活动预定表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-sub")
public class TSubController {

    @Autowired
    private TSubService tSubService;
    @PostMapping("/list")
    public JsonResult list(@RequestBody TSub item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tSubService.getList(item,currentPage,pageSize);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TSub item){
        QueryWrapper q = new QueryWrapper();
        q.eq("created_id",item.getCreatedId());
        q.eq("status","Already subscribe");
        List<TSub> list = tSubService.list(q);
        if(list.size()!=0){
            return JsonResult.fail(203,"Subscribe fail, Already subscribe.");
        }
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setStatus("Already subscribe");
        item.setCreatedTime(new Date());
        boolean res = tSubService.save(item);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TSub item){
        item.setModifiedTime(new Date());
        boolean res = tSubService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TSub tSub = tSubService.getById(id);
        System.out.println(tSub.toString());
        boolean res = tSubService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
}

