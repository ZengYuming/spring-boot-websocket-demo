package com.example.websocket.demo.client.payer;

import com.example.websocket.demo.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 付款方1
 */
public class Payer1 {
    private static Logger logger = LoggerFactory.getLogger(Payer1.class);
    //交易令牌
    private static final String transToken = "17111404060548491087";

    public static void main(String[] args) {
        try {
            SocketUtil helloClient = new SocketUtil();
            ListenableFuture<StompSession> f = helloClient.connect("ws://" + Consts.HOST + ":" + Consts.PORT + Consts.STOMP_END_POINT + "?" + Consts.USER_IDENTIFY_PARAM_NAME + "=" + transToken);
            StompSession stompSession = f.get();
            logger.info("开始使用session订阅问候语topic");
            logger.info("session:" + stompSession);
            helloClient.subscribeGreetings(Consts.USER_DESTINATION_PREFIX + Consts.BROKER_DESTINATION_QUEUE_PREFIX + Consts.DESTINATION, stompSession);
            Thread.sleep(600000);
        } catch (Exception ex) {
        }
    }
}
