package com.api.service.impl;

import com.api.mapper.TActMapper;
import com.api.pojo.TAct;
import com.api.service.TActService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 */
@Service
public class TActServiceImpl extends ServiceImpl<TActMapper, TAct> implements TActService {
    @Resource
    private TActMapper tActMapper;
    @Override
    public Page<TAct> getList(TAct item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TAct> wrapper = new MPJQueryWrapper<TAct>()
                .selectAll(TAct.class)
                .select("u1.username as created_name",
                        "u2.username as modified_name",
                        "u3.name as bqn")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id")
                .leftJoin("t_type u3 on u3.id = t.bq");
        wrapper.like(!Objects.isNull(item.getBqn())&&!item.getBqn().equals(""),
                "u3.name",item.getBqn());
        wrapper.like(!Objects.isNull(item.getAddress())&&!item.getAddress().equals(""),
                "t.address",item.getAddress());
        wrapper.eq(!Objects.isNull(item.getStime())&&!item.getStime().equals(""),
                "t.stime",item.getStime());
        wrapper.ge(!Objects.isNull(item.getLys())&&!item.getLys().equals(0),
                "t.ys",item.getLys());
        wrapper.le(!Objects.isNull(item.getYys())&&!item.getYys().equals(0),
                "t.ys",item.getYys());
        wrapper.like(!Objects.isNull(item.getName())&&!item.getName().equals(""),
                "t.name",item.getName());
        wrapper.like(!Objects.isNull(item.getBq())&&!item.getBq().equals(""),"t.bq",item.getBq());
        wrapper.eq(!Objects.isNull(item.getCreatedName())&&!item.getCreatedName().equals(""),"u1.name",item.getCreatedName());
        wrapper.eq(!Objects.isNull(item.getSs())&&!item.getSs().equals(""),"t.ss",item.getSs());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        wrapper.orderByDesc("created_time");
        Page<TAct> res = tActMapper.selectJoinPage(new Page<>(currentPage, pageSize), TAct.class, wrapper);
        return res;
    }
}
