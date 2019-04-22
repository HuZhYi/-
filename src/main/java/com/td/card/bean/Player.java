package com.td.card.bean;

import java.io.Serializable;

public class Player implements Serializable {

    private int playerId;

    private String playerName;

    private String playerPwd;

    private int victory;

    private int defeated;

    private int roomId;

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", playerPwd='" + playerPwd + '\'' +
                ", victory=" + victory +
                ", defeated=" + defeated +
                ", roomId=" + roomId +
                '}';
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

    public String getPlayerPwd() {
        return playerPwd;
    }

    public void setPlayerPwd(String playerPwd) {
        this.playerPwd = playerPwd;
    }

    public int getVictory() {
        return victory;
    }

    public void setVictory(int victory) {
        this.victory = victory;
    }

    public int getDefeated() {
        return defeated;
    }

    public void setDefeated(int defeated) {
        this.defeated = defeated;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
