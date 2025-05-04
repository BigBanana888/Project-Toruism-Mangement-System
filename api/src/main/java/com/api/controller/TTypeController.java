package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TType;
import com.api.service.TTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 类型表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-type")
public class TTypeController {

    @Autowired
    private TTypeService tTypeService;
    @PostMapping("/list")
    public JsonResult list(@RequestBody TType item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tTypeService.getList(item,currentPage,pageSize);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TType item){
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setCreatedTime(new Date());
        boolean res = tTypeService.save(item);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TType item){
        item.setModifiedTime(new Date());
        boolean res = tTypeService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TType tType = tTypeService.getById(id);
        System.out.println(tType.toString());
        boolean res = tTypeService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
}

