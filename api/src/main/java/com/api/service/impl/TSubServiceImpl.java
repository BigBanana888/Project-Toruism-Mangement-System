package com.api.service.impl;

import com.api.mapper.TSubMapper;
import com.api.pojo.TSub;
import com.api.service.TSubService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 活动预定表 服务实现类
 * </p>
 */
@Service
public class TSubServiceImpl extends ServiceImpl<TSubMapper, TSub> implements TSubService {
    @Resource
    private TSubMapper tSubMapper;
    @Override
    public Page<TSub> getList(TSub item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TSub> wrapper = new MPJQueryWrapper<TSub>()
                .selectAll(TSub.class)
                .select("u1.username as created_name",
                        "u2.username as modified_name",
                        "u3.name as aname",
                        "u3.stime as stime",
                        "u3.address as address")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id")
                .leftJoin("t_act u3 on u3.id = t.aid");
        wrapper.like(!Objects.isNull(item.getAname())&&!item.getAname().equals(""),"u3.name",item.getAname());
        wrapper.eq(!Objects.isNull(item.getAid())&&!item.getAid().equals(""),"t.aid",item.getAid());
        wrapper.eq(!Objects.isNull(item.getStatus())&&!item.getStatus().equals(""),"t.status",item.getStatus());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        Page<TSub> res = tSubMapper.selectJoinPage(new Page<>(currentPage, pageSize), TSub.class, wrapper);
        return res;
    }
}
