package com.api.service.impl;

import com.api.mapper.TComMapper;
import com.api.pojo.TCom;
import com.api.service.TComService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 帖子评论表 服务实现类
 * </p>
 */
@Service
public class TComServiceImpl extends ServiceImpl<TComMapper, TCom> implements TComService {
    @Resource
    private TComMapper tComMapper;
    @Override
    public Page<TCom> getList(TCom item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TCom> wrapper = new MPJQueryWrapper<TCom>()
                .selectAll(TCom.class)
                .select("u1.username as created_name",
                        "u2.username as modified_name",
                        "u3.name as bname")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id")
                .leftJoin("t_blog u3 on u3.id = t.bid");
        wrapper.like(!Objects.isNull(item.getBname())&&!item.getBname().equals(""),"u3.name",item.getBname());
        wrapper.eq(!Objects.isNull(item.getBid())&&!item.getBid().equals(""),"t.bid",item.getBid());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        Page<TCom> res = tComMapper.selectJoinPage(new Page<>(currentPage, pageSize), TCom.class, wrapper);
        return res;
    }
}
