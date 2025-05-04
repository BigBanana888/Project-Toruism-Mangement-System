package com.api.service;

import com.api.pojo.TEva;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评价表 服务类
 * </p>
 */
public interface TEvaService extends IService<TEva> {
    Page getList(TEva item, Integer current, Integer pageSize);
}
