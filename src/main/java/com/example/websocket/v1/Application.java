package com.example.websocket.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//开启WebSocket
@EnableWebSocket
@SpringBootApplication
public class Application implements WebSocketConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



    /**
     * 注册WebSocket处理类
     *
     * @param webSocketHandlerRegistry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //支持websocket 的 connection，指定counterHandler处理路径为/counter 的长连接请求
        webSocketHandlerRegistry.addHandler(counterHandler(), "/counter")
                //添加WebSocket握手请求的拦截器.
                .addInterceptors(new CounterHandler.CountHandshakeInterceptor());

        //不支持websocket的connenction,采用sockjs
        webSocketHandlerRegistry.addHandler(counterHandler(), "/sockjs/counter")
                //添加WebSocket握手请求的拦截器.
                .addInterceptors(new CounterHandler.CountHandshakeInterceptor()).withSockJS();
    }

    @Bean
    public CounterHandler counterHandler() {
        return new CounterHandler();
    }
}

