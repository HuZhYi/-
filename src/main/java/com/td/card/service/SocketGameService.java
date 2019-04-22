package com.td.card.service;

import com.td.card.dto.CommunMsg;

/**
 * 处理所有长连接的逻辑交互
 */
public interface SocketGameService {

    /**
     * 消息同步
     * @param communMsg
     */
    String messageSyn(CommunMsg communMsg);
}
