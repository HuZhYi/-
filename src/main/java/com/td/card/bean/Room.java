package com.td.card.bean;

import java.io.Serializable;

public class Room implements Serializable {

    private int roomId;

    private String roomName;

    private String roomContent;

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomContent='" + roomContent + '\'' +
                '}';
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomContent() {
        return roomContent;
    }

    public void setRoomContent(String roomContent) {
        this.roomContent = roomContent;
    }
}
