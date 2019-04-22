package com.td.card.controller;

import com.td.card.bean.Player;
import com.td.card.bean.Room;
import com.td.card.bean.Unit;
import com.td.card.controller.websocket.WebSocketServer;
import com.td.card.dao.PlayerDao;
import com.td.card.service.PlayerService;
import com.td.card.service.RoomService;
import com.td.card.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 处理所有短链接的请求操作
 */
@RestController
public class HttpGameController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UnitService unitService;

    private HttpSession session;
    /**
     * 登录
     * @param username
     * @param password
     */
    @RequestMapping(value = "/player/login",method = RequestMethod.POST)
    public Map<String,Object> login(String username, String password, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        session = request.getSession();
        Player player = playerService.login(username,password);
        if(player == null){
            map.put("type","ERROR");
            map.put("msg","账号或者密码错误");
        }else {
            if(session.getAttribute(player.getPlayerId()+"")!=null){
                map.put("type","ERROR");
                map.put("msg","玩家已登录");
            }else {
                session.setAttribute(player.getPlayerId()+"",player);
                map.put("type","SUCCESS");
                map.put("msg","登录成功");
                map.put("playerId",player.getPlayerId());
                map.put("playerName",player.getPlayerName());
            }
        }
        return map;
    }

    /**
     * 注册
     * @param username
     * @param password
     */
    @RequestMapping(value = "/player/register",method = RequestMethod.POST)
    public Map<String,Object> register(String username, String password){
        Map<String,Object> map = new HashMap<>();
        Player player = playerService.findByName(username);
        if(player == null){
            player = new Player();
            player.setPlayerName(username);
            player.setPlayerPwd(password);
            playerService.add(player);
            map.put("type","SUCCESS");
            map.put("msg","注册成功请重新登录");
        }else {
            map.put("type","ERROR");
            map.put("msg","用户名已存在");
        }
        return map;
    }

    /**
     * 根据id获取玩家信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/player/{id}",method = RequestMethod.GET)
    public Map<String,Object> getPlayer(@PathParam("id")int id){
        Player player = playerService.find(id).get(0);
        Map<String,Object> map = new HashMap<>();
        map.put("playerId",player.getPlayerId());
        map.put("playerName",player.getPlayerName());
        return map;
    }

    /**
     * 根据房间id查找房主信息
     * @param roomId
     * @return
     */
    @RequestMapping(value = "/owner/{id}",method = RequestMethod.GET)
    public Map<String,Object> getOwner(@PathParam("id")int roomId){
        Player player = WebSocketServer.roomMap.get(roomId).getPlayers().get(0);
        Map<String,Object> map = new HashMap<>();
        map.put("playerId",player.getPlayerId());
        map.put("playerName",player.getPlayerName());
        return map;
    }

    /**
     * 查看房间列表
     */
    @RequestMapping(value = "/page/roomList",method = RequestMethod.GET)
    public Map<String,Object> pageRoom(){
        Map<String,Object> map = new HashMap<>();
        List<Room> rooms = roomService.find(0);
        if(rooms.size() > 0){
            for(Room r : rooms){
                if(r.getRoomContent()==null||r.getRoomContent().equals("")){
                    r.setRoomContent("空");
                }
            }
            map.put("type","SUCCESS");
            map.put("msg","当前房间数量为："+rooms.size());
            map.put("count",rooms.size());
            map.put("data",rooms);
        }else {
            map.put("type","SUCCESS");
            map.put("msg","当前没有房间");
            map.put("count",rooms.size());
        }
        return map;
    }

    /**
     * 查看卡牌图鉴信息（获取到的所有卡牌实体会放置在客户端的静态对象中）
     */
    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public Map<String,Object> pageData(){
        Map<String,Object> map = new HashMap<>();
        map.put("Type","单位数据");
        List<Unit> units = unitService.find();
        map.put("data",units);
        return map;
    }

    /**
     * 创建房间
     */
    @RequestMapping(value = "/room/create",method = RequestMethod.POST)
    public Map<String,Object> createRoom(String roomName,String roomContent){
        Map<String,Object> map = new HashMap<>();
        Room room = new Room();
        room.setRoomName(roomName);
        room.setRoomContent(roomContent);
        int roomId = roomService.add(room);
        map.put("type","SUCCESS");
        map.put("msg","游戏房间["+roomName+"]已创建");
        map.put("roomId",roomId);
        return map;
    }

}
