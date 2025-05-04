package com.api.service;

import com.api.pojo.TNotice;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Notice表 服务类
 * </p>
 */
public interface TNoticeService extends IService<TNotice> {
    Page getList(TNotice item, Integer current, Integer pageSize);
}
