package com.api.service;

import com.api.pojo.TType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 类型表 服务类
 * </p>
 */
public interface TTypeService extends IService<TType> {
    Page getList(TType item, Integer current, Integer pageSize);
}
