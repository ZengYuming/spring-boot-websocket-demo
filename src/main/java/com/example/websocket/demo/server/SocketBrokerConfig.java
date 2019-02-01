package com.example.websocket.demo.server;

import com.example.websocket.demo.Consts;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * WebSocket配置类，定义socket连接、推送、订阅路径，以及关联用户鉴权带业务
 * 开启WebSocket，并启用 STOMP
 */
@EnableWebSocketMessageBroker
@Configuration
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //将“/register”注册为STOMP端点,客户端在订阅或发布消息到目的地路径前，要连接该端点
        registry.addEndpoint(Consts.STOMP_END_POINT)
                //自定义每个客户端对应的标识，用于服务端精准消息推送
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        //将客户端标识封装为Principal对象，从而让服务端能通过getName()方法找到指定客户端
                        Object o = attributes.get(Consts.USER_IDENTIFY_PARAM_NAME);
                        return new FastPrincipal(o.toString());
                    }
                })
                //添加socket拦截器，用于从请求中获取客户端标识参数
                .addInterceptors(new CountHandshakeInterceptor()).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //客户端发送消息的请求前缀
        config.setApplicationDestinationPrefixes(Consts.APP_DESTINATION_PREFIX);
        //客户端订阅消息的请求前缀，topic一般用于广播推送，queue用于点对点推送
        config.enableSimpleBroker(Consts.BROKER_DESTINATION_TOPIC_PREFIX, Consts.BROKER_DESTINATION_QUEUE_PREFIX);
        //服务端通知客户端的前缀，可以不设置，默认为user
        config.setUserDestinationPrefix(Consts.USER_DESTINATION_PREFIX);
    }

    class FastPrincipal implements Principal {
        private final String name;

        public FastPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
