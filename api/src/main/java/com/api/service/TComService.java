package com.api.service;

import com.api.pojo.TCom;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 帖子评论表 服务类
 * </p>
 */
public interface TComService extends IService<TCom> {
    Page getList(TCom item, Integer current, Integer pageSize);
}
