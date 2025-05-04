package com.api.service.impl;

import com.api.mapper.TBlogMapper;
import com.api.pojo.TBlog;
import com.api.service.TBlogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 */
@Service
public class TBlogServiceImpl extends ServiceImpl<TBlogMapper, TBlog> implements TBlogService {
    @Resource
    private TBlogMapper tBlogMapper;
    @Override
    public Page<TBlog> getList(TBlog item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TBlog> wrapper = new MPJQueryWrapper<TBlog>()
                .selectAll(TBlog.class)
                .select("u1.nickname as created_name",
                        "u2.nickname as modified_name",
                        "u1.image_url as uhead")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id");
        wrapper.like(!Objects.isNull(item.getName())&&!item.getName().equals(""),
                "t.name",item.getName());
        wrapper.eq(!Objects.isNull(item.getCreatedName())&&!item.getCreatedName().equals(""),"u1.name",item.getCreatedName());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        Page<TBlog> res = tBlogMapper.selectJoinPage(new Page<>(currentPage, pageSize), TBlog.class, wrapper);
        return res;
    }
}
