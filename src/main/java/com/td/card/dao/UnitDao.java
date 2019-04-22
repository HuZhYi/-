package com.td.card.dao;

import com.td.card.bean.Unit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UnitDao {

    @Select("select id, type, name, HP, atteck, cost, level, info from Unit")
    List<Unit> select();

}
