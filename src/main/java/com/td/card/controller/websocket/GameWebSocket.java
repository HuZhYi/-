package com.td.card.controller.websocket;

import com.td.card.bean.PlayerData;
import com.td.card.bean.PlayerSession;
import com.td.card.bean.Room;
import com.td.card.bean.Unit;
import com.td.card.dto.GameSession;
import com.td.card.service.PlayerService;
import com.td.card.service.RoomService;
import com.td.card.service.UnitService;
import com.td.card.util.SynGameData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{connectData}")
@Component
public class GameWebSocket {


    private static PlayerService playerService;
    @Autowired
    public void setPlayerService(PlayerService playerService){
        GameWebSocket.playerService = playerService;
    }

    private static RoomService roomService;
    @Autowired
    public void setRoomService(RoomService roomService){GameWebSocket.roomService = roomService;}

    private static UnitService unitService;
    @Autowired
    public  void setUnitService(UnitService unitService){GameWebSocket.unitService = unitService;}

    private PlayerData data;//玩家初始数据

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Map<Integer, GameSession> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("connectData")String connectData, Session session){
        int playerId = Integer.parseInt(connectData.split("_")[0]);
        int roomId = Integer.parseInt(connectData.split("_")[1]);

        data = new PlayerData();
        //判断是否有这个房间，有的话就创建，没有 的话直接加入
        GameSession gameSession = null;
        List<PlayerSession> playerSessions = null;
        if(!clients.containsKey(roomId)){
            logger.info("玩家"+playerId+"创建了房间"+roomId);
            gameSession = new GameSession();
            Room room = roomService.find(roomId).get(0);
            gameSession.setRoom(room);
            playerSessions = new ArrayList<>();
        }else {
            logger.info("玩家"+playerId+"加入了房间"+roomId);
            gameSession = clients.get(roomId);
            playerSessions = gameSession.getPlayers();
        }
        PlayerSession playerSession = new PlayerSession();
        playerSession.setPlayerId(playerId);
        playerSession.setPlayerName(playerService.find(playerId).get(0).getPlayerName());
        playerSession.setSession(session);
        //初始化玩家数据
        playerSession.setHP(data.getHP());
        playerSession.setMoney(data.getMoney());
        playerSession.setEff(data.getEfficiency());

        playerSessions.add(playerSession);
        gameSession.setPlayers(playerSessions);
        clients.put(roomId,gameSession);
    }

    @OnClose
    public void onClose(@PathParam("connectData")String connectData, Session session){
        int playerId = Integer.parseInt(connectData.split("_")[0]);
        int roomId = Integer.parseInt(connectData.split("_")[1]);

        clients.remove(roomId);
        logger.info("玩家"+playerId+"离开了游戏，房间"+roomId+"已销毁");
        roomService.delete(roomId);
    }

    @OnMessage
    public void onMessage(@PathParam("connectData")String connectData, Session session, String msg){
        int playerId = Integer.parseInt(connectData.split("_")[0]);
        int roomId = Integer.parseInt(connectData.split("_")[1]);
        logger.info("玩家"+playerId+"发送了消息："+msg);

        JSONObject object = JSONObject.fromObject(msg);

        Map<String,Object> map = new HashMap<>();
        List<PlayerSession> players = null;
        switch ((String)object.get("Type")){
            case "RoomInfo":
                map.put("Type","房间信息");
                map.put("roomName",roomService.find(roomId).get(0).getRoomName());
                players = clients.get(roomId).getPlayers();
                map.put("p1Name",players.get(0).getPlayerName());
                if(players.size()==2){
                    map.put("p2Name",players.get(1).getPlayerName());
                }else {
                    map.put("p2Name","空");
                }
                break;
            case "Ready":
                map.put("Type","准备");
                players = clients.get(roomId).getPlayers();
                if(playerId == players.get(0).getPlayerId()){
                    map.put("Ready","p1");
                    map.put("msg",players.get(0).getPlayerName()+"已准备");
                }else {
                    map.put("Ready","p2");
                    map.put("msg",players.get(1).getPlayerName()+"已准备");
                }
                break;
            case "PlayerStartInfo":
                map.put("Type","玩家初始信息");
                map.put("start.HP",data.getHP());
                map.put("start.money",data.getMoney());
                map.put("start.efficiency",data.getEfficiency());
                map.put("up.efficiency",data.getUpEfficiency());
                //发送谁先手
                players = clients.get(roomId).getPlayers();
                map.put("stepPlayerId",players.get(0).getPlayerId());
                break;
            case "Step":
                players = clients.get(roomId).getPlayers();
                //如果是后手玩家回合结束
                if(playerId == players.get(1).getPlayerId()){
                    int tStep = clients.get(roomId).getStepCount()+1;
                    clients.get(roomId).setStepCount(tStep);
                    //TODO 进入战斗回合，战斗回合结束后再向玩家发送继续游戏的信息
                    map.put("Type","战斗回合");
                    map.put("playerId",players.get(0).getPlayerId());
                }else {
                    map.put("Type","回合结束");
                    map.put("playerId",playerId);//判断是谁的id谁就禁用按钮
                    map.put("msg","玩家"+playerId+"回合结束");
                }
                break;
            case "FightStep":
                int fStep = clients.get(roomId).getFightCount()+1;
                clients.get(roomId).setFightCount(fStep);
                //双方都数据同步完了
                if(fStep == 2){
                    //清空数据
                    clients.get(roomId).setFightCount(0);
                    clients.get(roomId).getSynaorizedDatas().clear();

                    players = clients.get(roomId).getPlayers();
                    map.put("Type","回合结束");
                    map.put("playerId",players.get(1).getPlayerId());//将后手玩家按钮禁掉
                    map.put("msg","玩家"+players.get(0).getPlayerId()+"开始回合");
                }
                break;
            case "Fight"://战斗回合结束,同步数据
                clients.get(roomId).getSynaorizedDatas().add(JSONObject.fromObject(msg));
                if(clients.get(roomId).getSynaorizedDatas().size()==2){
                    map.put("Type","同步战斗数据");
                    map.put("playerId",playerId);//谁的数据，对方要将p1p2互换
                    logger.info(clients.get(roomId).getSynaorizedDatas().get(1).toString());
                    map.put("data",JSONObject.fromObject(msg));
                }
                break;
            case "Build":
                map.put("Type","建造");
                map.put("playerId",playerId);//谁行动
                map.put("unitId",(int)object.get("unitId"));
                map.put("posX",(int)object.get("posX"));
                map.put("posY",(int)object.get("posY"));
                map.put("currentMoney",(int)object.get("currentMoney"));
                players = clients.get(roomId).getPlayers();
                if(playerId == players.get(0).getPlayerId()){
                    players.get(0).setMoney((int)object.get("currentMoney"));
                }else {
                    players.get(1).setMoney((int)object.get("currentMoney"));
                }
                break;
            case "Defeated":
                map.put("Type","失败");
                map.put("playerId",playerId);//谁行动
                break;
        }


        JSONObject jsonObject = JSONObject.fromObject(map);
        Send(roomId,jsonObject.toString());
    }


    public void Send(int roomId, String msg){

        GameSession gameSession = clients.get(roomId);
        List<PlayerSession> playerSessions = gameSession.getPlayers();
        for(PlayerSession p : playerSessions){
            p.getSession().getAsyncRemote().sendText(msg);
        }
    }
}

