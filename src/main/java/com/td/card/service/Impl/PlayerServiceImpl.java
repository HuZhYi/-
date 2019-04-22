package com.td.card.service.Impl;

import com.td.card.bean.Player;
import com.td.card.dao.PlayerDao;
import com.td.card.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao playerDao;

    @Override
    public int add(Player player) {
        playerDao.insert(player);
        return player.getPlayerId();
    }

    @Override
    public int remove(int id) {
        return 0;
    }

    @Override
    public int edit(Player player) {
        return 0;
    }

    @Override
    public List<Player> find(int id) {
        if(id == 0) return playerDao.select();
        return playerDao.selectOne(id);
    }

    @Override
    public Player login(String username,String password) {
        return playerDao.login(username,password);
    }

    @Override
    public Player findByName(String username) {
        return playerDao.findByName(username);
    }
}
