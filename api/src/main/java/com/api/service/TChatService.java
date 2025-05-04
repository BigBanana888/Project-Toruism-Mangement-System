package com.api.service;

import com.api.pojo.TChat;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 对话表 服务类
 * </p>
 */
public interface TChatService extends IService<TChat> {
    Page getList(TChat item, Integer current, Integer pageSize);
}
