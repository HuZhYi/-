package com.td.card.dto;

import com.td.card.bean.PlayerSession;
import com.td.card.bean.Room;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameSession {

    private Room room;

    private List<JSONObject> synaorizedDatas = new ArrayList<>();//把双方数据收到后再统一发送

    private List<PlayerSession> players;

    private int stepCount = 0;

    private int fightCount = 0;

    public int getFightCount() {
        return fightCount;
    }

    public void setFightCount(int fightCount) {
        this.fightCount = fightCount;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public List<JSONObject> getSynaorizedDatas() {
        return synaorizedDatas;
    }

    public void setSynaorizedDatas(List<JSONObject> synaorizedDatas) {
        this.synaorizedDatas = synaorizedDatas;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<PlayerSession> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerSession> players) {
        this.players = players;
    }
}
