package com.api.controller;


import com.api.config.JsonResult;
import com.api.pojo.TEva;
import com.api.pojo.TSub;
import com.api.pojo.TUser;
import com.api.service.TEvaService;
import com.api.service.TSubService;
import com.api.service.TUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 评价表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/t-eva")
public class TEvaController {

    @Autowired
    private TEvaService tEvaService;
    @Autowired
    private TUserService tUserService;
    @PostMapping("/list")
    public JsonResult list(@RequestBody TEva item, @RequestParam int currentPage, @RequestParam int pageSize){
        Page res = tEvaService.getList(item,currentPage,pageSize);
        return JsonResult.success("Query success",res);
    }
    @PostMapping("/save")
    public JsonResult save(@RequestBody TEva item){
        String id = UUID.randomUUID().toString()
                .replace("-", "");
        item.setId(id);
        item.setCreatedTime(new Date());
        boolean res = tEvaService.save(item);
//        评价完成后
//        更新活动举办人评分
        QueryWrapper q = new QueryWrapper();
        q.eq("cid",item.getCid());
        List<TEva> evas = tEvaService.list(q);
        TUser tUser = tUserService.getById(item.getCid());
        int w = 0;
        if (evas.size()==0){
            tUser.setRate(item.getRate()*10);
        }else{
            for (TEva e :
                    evas) {
                w += e.getRate()*10;
            }
            tUser.setRate(w/evas.size());
        }
        tUserService.updateById(tUser);
        if(res){
            return JsonResult.success("Insert success");
        }else{
            return JsonResult.fail(203,"Insert fail");
        }
    }
    @PostMapping("/update")
    public JsonResult update(@RequestBody TEva item){
        item.setModifiedTime(new Date());
        boolean res = tEvaService.updateById(item);
        if(res){
            return JsonResult.success("Update success");
        }else{
            return JsonResult.fail(203,"Update fail");
        }
    }
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id){
        TEva tEva = tEvaService.getById(id);
        System.out.println(tEva.toString());
        boolean res = tEvaService.removeById(id);
        if(res){
            return JsonResult.success("Delete success");
        }else{
            return JsonResult.fail(204,"Delete fail");
        }
    }
}

