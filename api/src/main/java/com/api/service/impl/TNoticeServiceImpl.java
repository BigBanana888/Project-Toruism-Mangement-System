package com.api.service.impl;

import com.api.mapper.TNoticeMapper;
import com.api.pojo.TNotice;
import com.api.service.TNoticeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * Notice表 服务实现类
 * </p>
 */
@Service
public class TNoticeServiceImpl extends ServiceImpl<TNoticeMapper, TNotice> implements TNoticeService {
    @Resource
    private TNoticeMapper tNoticeMapper;
    @Override
    public Page<TNotice> getList(TNotice item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TNotice> wrapper = new MPJQueryWrapper<TNotice>()
                .selectAll(TNotice.class)
                .select("u1.username as created_name",
                        "u2.username as modified_name")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id");
        wrapper.like(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.like(!Objects.isNull(item.getName())&&!item.getName().equals(""),"t.name",item.getName());
        wrapper.like(!Objects.isNull(item.getContent()),"t.content",item.getContent());
        wrapper.eq(!Objects.isNull(item.getStatus())&&!item.getStatus().equals(-1),"t.status",item.getStatus());
        wrapper.orderByDesc("created_time");
        Page<TNotice> res = tNoticeMapper.selectJoinPage(new Page<>(currentPage, pageSize), TNotice.class, wrapper);
        return res;
    }
}
