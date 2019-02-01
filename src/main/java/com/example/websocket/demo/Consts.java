package com.example.websocket.demo;

public class Consts {
    public static final String HOST = "localhost";
    public static final Integer PORT = 8080;

    //将“/register”注册为STOMP端点,客户端在订阅或发布消息到目的地路径前，要连接该端点
    public static final String STOMP_END_POINT = "/register";
    //客户端发送消息的请求前缀
    public static final String APP_DESTINATION_PREFIX = "/app";
    //服务端通知客户端的前缀(即，客户端订阅的前缀)
    public static final String USER_DESTINATION_PREFIX = "/user";
    //客户端订阅消息的请求前缀，topic一般用于广播推送
    public static final String BROKER_DESTINATION_TOPIC_PREFIX="/topic";
    //客户端订阅消息的请求前缀，queue用于点对点推送
    public static final String BROKER_DESTINATION_QUEUE_PREFIX="/queue";
    //目的地，即，双方约定的地址，用于订阅和监听
    public static final String DESTINATION = "/notice";
    //用户表示参数名：transToken，用交易令牌来做双方的监听和订阅标识。
    public static final String USER_IDENTIFY_PARAM_NAME = "transToken";
}
