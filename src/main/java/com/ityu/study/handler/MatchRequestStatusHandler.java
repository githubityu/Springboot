package com.ityu.study.handler;

import com.alibaba.fastjson.JSON;
import com.ityu.study.bo.OrderMessageBO;
import com.ityu.study.repositories.StudentInfoBeanRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihe
 */
@Component
@Slf4j
public class MatchRequestStatusHandler extends TextWebSocketHandler {

    /**
     * 帮手服务订单前缀
     */
    private static final String HELPER_PREFIX = "H";
    /**
     * 家政服务订单前缀
     */
    private static final String HOUSEKEEPING_PREFIX = "HO";


    private final Map<String, WebSocketSession> SESSION_HOlDER = new ConcurrentHashMap<>();


    private final StudentInfoBeanRepository orderRepository;


    public MatchRequestStatusHandler(StudentInfoBeanRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    /**
     * 监听家政服务订单匹配成功或失败消息
     *
     * @param message 队列发送的消息
     */
    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    @RabbitListener(queues = "sc.mq.u.hkod.mc.notify")
    public void process(@Payload OrderMessageBO message) {
        log.debug("收到来自mq的消息:{}", message);


        //处理匹配失败情况，该操作由延迟队列触发
        if (Objects.isNull(message.getSupplierId()) && message.getStatus() <= 10) {
            log.debug("开始检查订单状态---------------------------------------->");
            log.debug("订单状态检查完成---------------------------------------->");
        } else {
            log.debug("订单匹配成功,状态:{}", message.getStatus());
        }

        this.notifyOrderStatusChange(message.getId(), HOUSEKEEPING_PREFIX);
        log.debug("消息处理完毕:{}", message);
    }


    /**
     * 监听帮手服务订单匹配成功或失败消息
     *
     * @param message 队列发送的消息
     */
    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    @RabbitListener(queues = "smart-community.helper.order-auto-failed")
    public void processHelperOrder(@Payload OrderMessageBO message) {
        log.debug("收到来自mq的消息:{}", message);
        this.notifyOrderStatusChange(message.getId(), HELPER_PREFIX);
        log.debug("消息处理完毕:{}", message);
    }


    private void notifyOrderStatusChange(Integer orderId, String prefix) {
        log.debug("当前用户数量:{}", SESSION_HOlDER.size());
        log.trace("当前的用户为:{}", SESSION_HOlDER);
        log.debug("当前监听的订单为:{}", this.SESSION_HOlDER.keySet());
        WebSocketSession session = this.SESSION_HOlDER.get(prefix + orderId);
        log.debug("匹配到的用户信息为:<----------{}------>", session);
        if (Objects.nonNull(session)) {
            this.send(session, new ResponseMessage(CommEnum.matched, String.valueOf(orderId)));
            log.debug("发送订单匹配成功请求结束：{}", orderId);
        }
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.send(session, new ResponseMessage(CommEnum.submit, null));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        log.debug("ws message in:{}", message.getPayload());
        RequestMessage msg = JSON.parseObject(message.getPayload(), RequestMessage.class);

        switch (msg.cmd) {
            case echo:
                sendEcho(session, message.getPayload());
                break;
            case submit:
                this.handlerClientSubmit(session, msg);
                break;
            case noAction:
                break;
            default:
                break;
        }
        log.debug("message process end");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("session will removeSession" + session.getId());
        SESSION_HOlDER.keySet()
                .stream()
                .filter(x -> Objects.isNull(SESSION_HOlDER.get(x)) || SESSION_HOlDER.get(x).isOpen())
                .forEach(SESSION_HOlDER::remove);
        log.info("session removeSession status:" + status);
    }


    private void handlerClientSubmit(WebSocketSession session, RequestMessage cmd) {
        log.debug("新用户注册:{}", session);
        log.debug("新用户注册:{}", cmd);
        this.SESSION_HOlDER.put(cmd.getData(), session);
        log.debug("当前用户数量:{}", SESSION_HOlDER.size());
    }


    private void sendEcho(WebSocketSession session, String payload) {
        this.send(session, new ResponseMessage(CommEnum.noAction, payload));
    }


    private void send(WebSocketSession session, ResponseMessage message) {
        try {
            session.sendMessage(new TextMessage(JSON.toJSONString(message)));
        } catch (Exception e) {
            log.error("send err", e);
        }
    }


    @SuppressWarnings("unused")
    private enum CommEnum {
        /**
         * 请求回环
         */
        echo,
        /**
         * 请求提交信息
         */
        submit,
        /**
         * 不执行任何操作
         */
        noAction,
        /**
         * 匹配成功
         */
        matched,
        /**
         * removeSession
         */
        close


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class RequestMessage {
        private CommEnum cmd;
        private Integer userId;
        private String data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static
    class ResponseMessage {
        private CommEnum cmd;
        private String data;
    }


}
