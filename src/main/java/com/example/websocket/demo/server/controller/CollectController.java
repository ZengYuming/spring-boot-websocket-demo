package com.example.websocket.demo.server.controller;

import com.example.websocket.demo.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("collect")
public class CollectController {
    //Socket消息模版类，用来向客户端推送消息
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 申请收款
     *
     * @param memberCode 付款方的memberCode
     * @param transToken 付款方的交易令牌
     * @param amount     交易金额
     * @return
     */
    @GetMapping("apply")
    public String apply(String memberCode, String transToken, Double amount) {
        //todo:根据accessToken从SecurityContext里面获取用户凭证，验证这个transToken是否属于该用户
        System.out.println("成功收取（member=" + memberCode + "）(transToken=" + transToken + ")" + amount + "元");
        //这里定义了订阅消息的路径是"/queue/notice"，客户端请求的路径则为："/user/queue/notice"
        //通知客户端（付款方）付款成功
        simpMessagingTemplate.convertAndSendToUser(transToken, Consts.BROKER_DESTINATION_QUEUE_PREFIX + Consts.DESTINATION, "付款成功，交易令牌(" + transToken + ")，交易金额(" + amount + "),交易日期(" + new Date() + ")");
        return "收款成功";
    }
}
