package com.api.mapper;

import com.api.pojo.NameNum;

import java.util.List;

public interface ShowMapper {
    List<NameNum> selectActLocationHeat();
    List<NameNum> selectBudgetDistribution();
    List<NameNum> selectRecentSubscriptions();
}
