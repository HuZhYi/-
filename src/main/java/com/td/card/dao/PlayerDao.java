package com.td.card.dao;

import com.td.card.bean.Player;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayerDao {

    @Options(useGeneratedKeys = true,keyProperty = "playerId")
    @Insert("insert into player(playerName,playerPwd) values(#{playerName},#{playerPwd})")
    int insert(Player player);

    @Delete("delete from player where playerId = #{id}")
    int delete(int id);

    @Update("update player set playerName = #{playerName},playerPwd = #{playerPwd}," +
            "victory = #{victory},defeated = #{defeated},roomId = #{roomId} where playerId = #{playerId}")
    int update(Player player);

    @Select("select playerId,playerName,playerPwd,victory,defeated,roomId from player")
    List<Player> select();

    @Select("select playerId,playerName,playerPwd,victory,defeated,roomId from player where playerId = #{id}")
    List<Player> selectOne(int id);

    @Select("select playerId,playerName,playerPwd,victory,defeated,roomId from player where " +
            "playerName = #{username} and playerPwd = #{password}")
    Player login(String username,String password);

    @Select("select playerId,playerName,playerPwd,victory,defeated,roomId from player where " +
            "playerName = #{username}")
    Player findByName(String username);

}
