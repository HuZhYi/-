package com.td.card.service;

import com.td.card.bean.Player;

import java.util.List;

public interface PlayerService {

    int add(Player player);

    int remove(int id);

    int edit(Player player);

    List<Player> find(int id);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Player login(String username,String password);

    Player findByName(String username);
}
