package com.td.card.controller;

import com.td.card.bean.Player;
import com.td.card.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;


    @RequestMapping("/find")
    public List<Player> playerList(){
        return playerService.find(0);
    }

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Player login(String username,String password){
        Player player = playerService.login(username,password);
        return player;
    }

    /**
     * 注册
     * @param username 账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public int register(String username,String password){
        Player player = playerService.findByName(username);
        if(player != null) return 0;
        player = new Player();
        player.setPlayerName(username);
        player.setPlayerPwd(password);
        return playerService.add(player);
    }

    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public String jsonTest(@RequestBody Map<String,Object> map){
        if(map == null) return null;
        return "hello";
    }

}
