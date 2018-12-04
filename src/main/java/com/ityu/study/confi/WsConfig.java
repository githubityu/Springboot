package com.ityu.study.confi;


import com.ityu.study.handler.MatchRequestStatusHandler;
import com.ityu.study.util.UrlBaseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * ws 服务配置类
 *
 * @author lihe
 */
@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {


    private final MatchRequestStatusHandler matchRequestStatusHandler;

    @Autowired
    public WsConfig(MatchRequestStatusHandler matchRequestStatusHandler) {
        this.matchRequestStatusHandler = matchRequestStatusHandler;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(matchRequestStatusHandler, UrlBaseConstant.V1.concat("/ws/hrs"));
    }
}
