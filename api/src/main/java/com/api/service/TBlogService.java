package com.api.service;

import com.api.pojo.TBlog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 帖子表 服务类
 * </p>
 */
public interface TBlogService extends IService<TBlog> {
    Page getList(TBlog item, Integer current, Integer pageSize);
}
