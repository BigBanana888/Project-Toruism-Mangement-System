package com.api.service.impl;

import com.api.mapper.TTypeMapper;
import com.api.pojo.TType;
import com.api.service.TTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 类型表 服务实现类
 * </p>
 */
@Service
public class TTypeServiceImpl extends ServiceImpl<TTypeMapper, TType> implements TTypeService {
    @Resource
    private TTypeMapper tTypeMapper;
    @Override
    public Page<TType> getList(TType item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TType> wrapper = new MPJQueryWrapper<TType>()
                .selectAll(TType.class)
                .select("u1.username as created_name",
                        "u2.username as modified_name")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id");
        wrapper.like(!Objects.isNull(item.getName())&&!item.getName().equals(""),"t.name",
                item.getName());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        Page<TType> res = tTypeMapper.selectJoinPage(new Page<>(currentPage, pageSize), TType.class, wrapper);
        return res;
    }
}
