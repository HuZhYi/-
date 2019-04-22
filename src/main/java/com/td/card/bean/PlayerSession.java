package com.td.card.bean;

import javax.websocket.Session;

public class PlayerSession {

    private int playerId;

    private String playerName;

    private Session session;

    //TODO 数据同步校验
    private int HP;

    private int money;

    private float eff;

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public float getEff() {
        return eff;
    }

    public void setEff(float eff) {
        this.eff = eff;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
