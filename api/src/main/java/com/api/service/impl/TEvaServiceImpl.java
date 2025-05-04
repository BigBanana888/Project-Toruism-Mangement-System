package com.api.service.impl;

import com.api.mapper.TEvaMapper;
import com.api.pojo.TEva;
import com.api.service.TEvaService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 评价表 服务实现类
 * </p>
 */
@Service
public class TEvaServiceImpl extends ServiceImpl<TEvaMapper, TEva> implements TEvaService {
    @Resource
    private TEvaMapper tEvaMapper;
    @Override
    public Page<TEva> getList(TEva item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TEva> wrapper = new MPJQueryWrapper<TEva>()
                .selectAll(TEva.class)
                .select("u1.username as created_name","u1.image_url as ah",
                        "u2.username as modified_name",
                        "u3.name as aname")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id")
                .leftJoin("t_act u3 on u3.id = t.aid");
        wrapper.like(!Objects.isNull(item.getAname())&&!item.getAname().equals(""),"u3.name",item.getAname());
        wrapper.eq(!Objects.isNull(item.getAid())&&!item.getAid().equals(""),"t.aid",item.getAid());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        Page<TEva> res = tEvaMapper.selectJoinPage(new Page<>(currentPage, pageSize), TEva.class, wrapper);
        return res;
    }
}
