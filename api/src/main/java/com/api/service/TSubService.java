package com.api.service;

import com.api.pojo.TSub;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动预定表 服务类
 * </p>
 */
public interface TSubService extends IService<TSub> {
    Page getList(TSub item, Integer current, Integer pageSize);
}
