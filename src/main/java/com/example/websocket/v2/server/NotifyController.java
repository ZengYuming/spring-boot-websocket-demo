package com.example.websocket.v2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("notify")
public class NotifyController {
    //Socket消息模版类，用来向客户端推送消息
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("notice")
    public String notice(String name) {
        //这里定义了订阅消息的路径是"/queue/notice"，客户端请求的路径则为："/user/queue/notice"
        simpMessagingTemplate.convertAndSendToUser(name, "/queue/notice", "当前时间是：" + new Date());
        return "已发送";
    }
}
