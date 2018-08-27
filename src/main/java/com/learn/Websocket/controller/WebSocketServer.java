package com.learn.Websocket.controller;

import com.learn.Websocket.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocketServer
 * 主要负责webscoket链接的接入和关闭
 * @author vate
 *
 */
//由于是websocket 所以原本是@RestController的http形式 
//直接替换成@ServerEndpoint即可，作用是一样的 就是指定一个地址
//表示定义一个websocket的Server端
@Component
@ServerEndpoint(value = "/websocket/{userId}")
@Slf4j
public class WebSocketServer {

    /**
     * 连接事件 加入注解
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session session) {
        String message = "有新游客[" + userId + "]加入聊天室!";
        log.info(message);
        WebSocketUtil.addSession(userId, session);
        //此时可向所有的在线通知 某某某登录了聊天室
        WebSocketUtil.sendMessageForAll(message);
    }

    @OnClose
    public void onClose(@PathParam(value = "userId") String userId, Session session) {
        String message = "游客[" + userId + "]退出聊天室!";
        log.info(message);
        WebSocketUtil.removeSession(userId);
        //此时可向所有的在线通知 某某某登录了聊天室
        WebSocketUtil.sendMessageForAll(message);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "userId") String userId, String message) {
        //类似群发
        String info = "游客[" + userId + "]：" + message;
        log.info(info);
        WebSocketUtil.sendMessageForAll(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("异常:", throwable);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}