package com.td.card.dao;

import com.td.card.bean.Room;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoomDao {

    @Options(useGeneratedKeys = true,keyProperty = "roomId")
    @Insert("insert into room(roomName,roomContent) values(#{roomName},#{roomContent})")
    int insert(Room room);

    @Delete("delete from room where roomId = #{roomId}")
    int delete(@Param("roomId") int roomId);

    @Update("update room set roomName = #{roomName}, roomContent = #{roomContent} where roomId = #{roomId}")
    int update(Room room);

    @Select("select roomId, roomName, roomContent from room")
    List<Room> select();

    @Select("select roomId, roomName, roomContent from room where roomId = #{roomId}")
    List<Room> selectOne(@Param("roomId")int roomId);

}
