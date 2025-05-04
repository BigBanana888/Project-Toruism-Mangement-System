package com.api.service.impl;

import com.api.mapper.TChatMapper;
import com.api.pojo.TChat;
import com.api.service.TChatService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 对话表 服务实现类
 * </p>
 */
@Service
public class TChatServiceImpl extends ServiceImpl<TChatMapper, TChat> implements TChatService {
    @Resource
    private TChatMapper tChatMapper;
    @Override
    public Page<TChat> getList(TChat item, Integer currentPage, Integer pageSize) {
        MPJQueryWrapper<TChat> wrapper = new MPJQueryWrapper<TChat>()
                .selectAll(TChat.class)
                .select("u1.username as created_name",
                        "u2.username as modified_name",
                        "u3.username as aname",
                        "u3.image_url as image_url",
                        "u1.image_url as ch")
                .leftJoin("t_user u1 on u1.id = t.created_id")
                .leftJoin("t_user u2 on u2.id = t.modified_id")
                .leftJoin("t_user u3 on u3.id = t.aid");
        wrapper.like(!Objects.isNull(item.getAname())&&!item.getAname().equals(""),"u3.username",item.getAname());
        wrapper.like(!Objects.isNull(item.getCreatedName())&&!item.getCreatedName().equals(""),"u1.username",item.getCreatedName());
        wrapper.eq(!Objects.isNull(item.getAid())&&!item.getAid().equals(""),"t.aid",item.getAid());
        wrapper.eq(!Objects.isNull(item.getSs())&&!item.getSs().equals(""),"t.ss",item.getSs());
        wrapper.eq(!Objects.isNull(item.getCreatedId())&&!item.getCreatedId().equals(""),"t.created_id",item.getCreatedId());
        wrapper.orderByDesc("created_time");
        Page<TChat> res = tChatMapper.selectJoinPage(new Page<>(currentPage, pageSize), TChat.class, wrapper);
        return res;
    }
}
