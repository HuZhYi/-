package com.td.card.dto;

import com.td.card.bean.Player;
import com.td.card.bean.Room;

import java.util.List;

/**
 * 游戏房间对象
 */
public class GameRoom {

    private Room room;

    private List<Player> players;

    @Override
    public String toString() {
        return "GameRoom{" +
                "room=" + room +
                ", players=" + players +
                '}';
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
