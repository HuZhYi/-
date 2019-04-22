package com.td.card.service.Impl;

import com.td.card.bean.Unit;
import com.td.card.dao.UnitDao;
import com.td.card.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitDao unitDao;

    @Override
    public List<Unit> find() {
        return unitDao.select();
    }
}
