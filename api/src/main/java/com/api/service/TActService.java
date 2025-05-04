package com.api.service;

import com.api.pojo.TAct;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动表 服务类
 * </p>
 */
public interface TActService extends IService<TAct> {
    Page getList(TAct item, Integer current, Integer pageSize);
}
