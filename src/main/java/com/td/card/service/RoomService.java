package com.td.card.service;

import com.td.card.bean.Room;

import java.util.List;

public interface RoomService {

    int add(Room room);

    int delete(int roomId);

    int edit(Room room);

    List<Room> find(int id);
}
