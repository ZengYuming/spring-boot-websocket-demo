package com.example.websocket.demo.server;

import com.example.websocket.demo.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 握手拦截器
 * Socket拦截器，将客户端请求的参数存储至SocketSession中
 * 检查握手请求和响应, 对WebSocketHandler传递属性
 */
public class CountHandshakeInterceptor implements HandshakeInterceptor {
    private AtomicInteger counter = new AtomicInteger();
    private static Logger logger = LoggerFactory.getLogger(CountHandshakeInterceptor.class);

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
        String accessToken = ((ServletServerHttpRequest) request).getServletRequest().getParameter(Consts.USER_IDENTIFY_PARAM_NAME);
        logger.info("开始握手");
        logger.info("设置参数到websocket的session里面");
        logger.info("Consts.USER_IDENTIFY_PARAM_NAME：" + accessToken);
        logger.info("当前正在订阅的人数：" + counter.incrementAndGet());
        //保存客户端标识
        attributes.put(Consts.USER_IDENTIFY_PARAM_NAME, accessToken);
        return true;
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
        logger.info("握手结束");
    }
}
