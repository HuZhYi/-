package com.td.card.controller.websocket;

import com.td.card.bean.Player;
import com.td.card.bean.Room;
import com.td.card.dto.CommunMsg;
import com.td.card.dto.GameRoom;
import com.td.card.service.PlayerService;

import com.td.card.service.RoomService;
import com.td.card.service.SocketGameService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * websocket服务端控制（长连接--进入房间后）
 */
//@ServerEndpoint("/websocket/{connectData}")
//@Component
public class WebSocketServer {

    private static PlayerService playerService;
    @Autowired
    public void setPlayerService(PlayerService playerService){
        WebSocketServer.playerService = playerService;
    }

    private static RoomService roomService;
    @Autowired
    public void setRoomService(RoomService roomService){WebSocketServer.roomService = roomService;}

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 以玩家id为Key websocket为对象保存下来
     */
    private static Map<Integer,WebSocketServer> clients = new ConcurrentHashMap<>();
    /**
     * 房间id为key，房间为对象
     * 房间内有两个玩家的id
     */
    public static Map<Integer, GameRoom> roomMap = new HashMap<>();

    /**
     * 会话
     */
    private Session session;
    /**
     * 玩家对象
     */
    private Player player;//自己

    private Player player2;//对方


    /**
     * 建立连接
     * @param connectData
     * @param session
     */
//    @OnOpen
    public void onOpen(@PathParam("connectData")String connectData,Session session){
        System.out.println(connectData);
        //将玩家id与房间id传入
        String[] ids = connectData.split("_");
        int playerId = Integer.parseInt(ids[0]);
        int roomId = Integer.parseInt(ids[1]);

        this.player = playerService.find(playerId).get(0);//将玩家对象放入
        boolean owner = false;
        //如果没有这个房间，说明该玩家为房主(默认先手)
        if(!roomMap.containsKey(roomId)){
            owner = true;
            logger.info("创建新的房间，id为"+roomId);
            GameRoom gr = new GameRoom();
            Room r = roomService.find(roomId).get(0);
            gr.setRoom(r);
            List<Player> ps = new ArrayList<>();
            ps.add(this.player);
            gr.setPlayers(ps);
            roomMap.put(roomId,gr);
        }else {
            logger.info("加入房间，id为"+roomId);
            GameRoom gr = roomMap.get(roomId);
            List<Player> ps2 = gr.getPlayers();
            this.player2 = ps2.get(0);
            ps2.add(this.player);
            gr.setPlayers(ps2);
            roomMap.put(roomId,gr);
        }
        clients.put(this.player.getPlayerId(),this);
        logger.info(this.player.getPlayerName()+":::"+this.player2.getPlayerName());
        this.session = session;
        logger.info("》》》》》》");
        logger.info("当前连接客户id："+this.player.getPlayerId()+" 用户名："+this.player.getPlayerName());

//        try {
//            if(!owner){
//                //新玩家进入向房主推送消息
//                sendMessageOne(player.getPlayerId()+":ENTER",this.player2.getPlayerId());
//                //向新玩家推送房主消息
//                sendMessageOne(this.player2.getPlayerId()+":ENTER",this.player.getPlayerId());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.info("网络错误");
//        }
    }

    /**
     * 服务器错误
     * @param session
     * @param error
     */
//    @OnError
    public void onError(Session session, Throwable error){
        logger.info("服务器错误"+error.getMessage());
    }

    /**
     * 连接关闭
     */
//    @OnClose
    public void onClose(){
        clients.remove(player.getPlayerId());
//        try {
//            sendMessageOne(player.getPlayerName()+":PLAYER_EXIT",this.player2.getPlayerId());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.info("网络错误");
//        }
    }

    /**
     * 收到客户端得消息
     * @param message
     * @param session
     */
//    @OnMessage
    public void onMessage(String message,Session session) throws IOException, InterruptedException {

        JSONObject jo = JSONObject.fromObject(message);
        CommunMsg msg = (CommunMsg)JSONObject.toBean(jo);
        switch (msg.getMsgType()){
            case "NEW_PLAYER"://房主将新玩家id加入map，新玩家进入发送信息
                GameRoom gr = roomMap.get(msg.getRoomId());
                this.player2 = gr.getPlayers().get(1);
                break;
            case "STEP":
                sendMessageOne(msg.getAddresseeId()+":STEP_START",msg.getAddresseeId());
                break;
            case "OVER"://谁输谁发
                sendMessageOne(msg.getSenderId()+":DEFEATED",msg.getSenderId());
                Player p1 = playerService.find(msg.getSenderId()).get(0);
                p1.setDefeated(p1.getDefeated()+1);
                playerService.edit(p1);
                sendMessageOne(msg.getAddresseeId()+":WIN",msg.getAddresseeId());
                Player p2 = playerService.find(msg.getAddresseeId()).get(0);
                p2.setDefeated(p2.getVictory()+1);
                playerService.edit(p2);
                break;
            case "CLOSE":
                clients.remove(msg.getSenderId());
                //检查房间内是否有其它人
                GameRoom gr2 = roomMap.get(msg.getRoomId());
                for(Player p : gr2.getPlayers()){
                    if(p.getPlayerId() == msg.getSenderId())
                        gr2.getPlayers().remove(p);
                }
                //房间没人了
                if(gr2.getPlayers().size() == 0){
                    roomMap.remove(msg.getRoomId());
                }else {
                    //xx已经离开游戏
                    sendMessageOne(msg.getSenderId()+":PLAYER_EXIT",msg.getAddresseeId());
                }
                break;
            case "START":
                if(roomMap.get(msg.getRoomId()).getPlayers().size()==2){
                    sendMessageOne("TRUE:START",msg.getSenderId());
                    sendMessageOne("TRUE:START",msg.getAddresseeId());
                }else {
                    sendMessageOne("FALSE:START",msg.getSenderId());
                }
            default:
                sendMessageOne(message,msg.getAddresseeId());
                break;
        }

    }


    /**
     * 私聊
     * @param message
     * @param playerId
     * @throws IOException
     */
    public void sendMessageOne(String message,int playerId) throws IOException{
        WebSocketServer wss = clients.get(playerId);
        wss.session.getAsyncRemote().sendText(message);
    }




}
