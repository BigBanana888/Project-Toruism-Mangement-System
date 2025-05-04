package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TChat;
import com.api.service.TChatService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 对话表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-chat")
public class TChatController {

    @Autowired
    private TChatService tChatService;
    @PostMapping("/list")
    public JsonResult list(@RequestBody TChat item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tChatService.getList(item,currentPage,pageSize);
        for (Object c: res.getRecords()
             ) {
            System.out.println(c.toString());
        }
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TChat item){
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setSs("viewed");
        item.setCreatedTime(new Date());
        boolean res = tChatService.save(item);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TChat item){
        item.setModifiedTime(new Date());
        boolean res = tChatService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TChat tChat = tChatService.getById(id);
        System.out.println(tChat.toString());
        boolean res = tChatService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
}

