package com.example.websocket.v1;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Socket处理类
public class CounterHandler extends TextWebSocketHandler {
    public static final String COLLECTOR = "collector";
    private static final List<WebSocketSession> COUNTS = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connection established");
        COUNTS.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        System.out.println("Received:" + message.getPayload());
    }

    /**
     * 给某个用户发送消息
     *
     * @param count
     * @param message
     */
    public void sendMessageToUser(String count, String message) {
        //遍历记录的session，取出符合条件的session发送消息
        for (WebSocketSession socketSession : COUNTS) {
            if (socketSession.getAttributes().get(COLLECTOR).equals(count)) {
                try {
                    if (socketSession.isOpen()) {
                        //最关键的一句，给客户端推送消息
                        socketSession.sendMessage(new TextMessage(message));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                COUNTS.remove(socketSession);
                break;
            }
        }
    }


    /**
     * 检查握手请求和响应, 对WebSocketHandler传递属性
     */
    public static class CountHandshakeInterceptor implements HandshakeInterceptor {
        /**
         * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false.
         * 通过attributes参数设置WebSocketSession的属性
         *
         * @param request
         * @param response
         * @param wsHandler
         * @param attributes
         * @return
         * @throws Exception
         */
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            String collector = ((ServletServerHttpRequest) request).getServletRequest().getParameter(COLLECTOR);
            if (StringUtils.isEmpty(collector)) {
                return false;
            } else {
                attributes.put(COLLECTOR, collector);
                return true;
            }
        }

        /**
         * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
         *
         * @param request
         * @param response
         * @param wsHandler
         * @param exception
         */
        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {

        }
    }

}
