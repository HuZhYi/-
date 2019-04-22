package com.td.card.service.Impl;

import com.td.card.bean.Room;
import com.td.card.dao.RoomDao;
import com.td.card.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public int add(Room room) {
        roomDao.insert(room);
        return room.getRoomId();
    }

    @Override
    public int delete(int roomId) {
        return roomDao.delete(roomId);
    }

    @Override
    public int edit(Room room) {
        return roomDao.update(room);
    }

    @Override
    public List<Room> find(int id) {
        if(id == 0)
            return roomDao.select();
        else
            return roomDao.selectOne(id);
    }
}
