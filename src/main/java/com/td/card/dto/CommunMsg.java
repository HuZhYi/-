package com.td.card.dto;

import java.util.Map;

/**
 * 通信内容
 */
public class CommunMsg {

    private int senderId;//发送人id

    private int addresseeId;//收信人Id

    private int roomId;//房间id

    private String msgType;//消息类别

    private Map<String,Object> data;//消息内容
    /*出牌消息
    {
        "cardId": "001",  卡牌id
        "currentCost": 10,  当前剩多少费（扣完的）
        "postion": 1  建在哪个位置
    }
    */
     /*牌被扣血
    {
        "cardId": "001",  卡牌id
        "currentHP": 10,  当前剩多少血（扣完的）
        "postion": 1  建在哪个位置
    }
    */
     //如果是回合结束，判断如果是step则为该玩家回合已结束
    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(int addresseeId) {
        this.addresseeId = addresseeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
