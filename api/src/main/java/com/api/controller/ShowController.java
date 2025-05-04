package com.api.controller;


import com.api.config.JsonResult;
import com.api.mapper.ShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    private ShowMapper showMapper;
    @GetMapping("/selectActLocationHeat")
    JsonResult selectActLocationHeat(){
        return JsonResult.success("",showMapper.selectActLocationHeat());
    };
    @GetMapping("/selectBudgetDistribution")
    JsonResult selectBudgetDistribution(){
        return JsonResult.success("",showMapper.selectBudgetDistribution());
    };
    @GetMapping("/selectRecentSubscriptions")
    JsonResult selectRecentSubscriptions(){
        return JsonResult.success("",showMapper.selectRecentSubscriptions());
    };
}
