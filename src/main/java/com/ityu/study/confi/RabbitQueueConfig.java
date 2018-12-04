package com.ityu.study.confi;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * mq配置
 * 这里的配置应该使用topic exchange 记得优化一下
 *
 * @author lihe
 */
@Configuration
public class RabbitQueueConfig {
    /**
     * 新订单交换器 newOrderExchange
     */
    public static final String N_ORDER_EXCHANGE = "sc.ex.f.hkod.new";
    /**
     * 匹配状态交换器 order.match.change.exchange
     */
    public static final String ORDER_MATCH_CHANGE_EXCHANGE = "sc.ex.f.hkod.mc.change";

    /**
     * 订单状态切换信息交换器 order.status.change.exchange
     */
    public static final String ORDER_CHANGE_EXCHANGE = "sc.ex.f.hkod.change";


    /**
     * 新订单匹配队列 order.supplier.match.message
     */
    private static final String SUPPLIER_MATCH_MESSAGE = "sc.mq.sp.hkod.mc";

    /**
     * 服务端匹配取消通知 order.s.match.cancel.message
     */
    private static final String ORDER_S_MATCH_NOTICE_MESSAGE = "sc.mq.sp.hkod.mc.cancel";


    /**
     * 用户端匹配消息 order.s.match.user.notify.message
     */
    private static final String ORDER_U_MATCH_NOTICE_MESSAGE = "sc.mq.u.hkod.mc.notify";

    /**
     * 服务端匹配取消数据清理消息 order.s.match.cancel.clean
     */
    private static final String ORDER_S_MATCH_NOTICE_CLEAN_MESSAGE = "sc.mq.sp.hkod.mc.cancel.clean";

    /**
     * vendor 订单状态监听队列
     */
    private static final String ORDER_STATUS_CHANGE_VENDOR = "sc.mq.sp.hkod.status";
    /**
     * vendor 订单消息队列
     */
    private static final String ORDER_VENDOR_NONFICTION = "sc.mq.sp.hkod.nonfiction";

    /**
     * 用户订单状态监听队列
     */
    private static final String ORDER_STATUS_CHANGE_USER = "sc.mq.u.hkod.nonfiction";

    /**
     * 订单延迟消息队列,自动匹配超时
     */
    private static final String ORDER_DELAY_MESSAGE = "sc.mq.hkod.delay.auto.mf";


    /**
     * 匹配消息
     */
    @Bean
    public Queue oderMatchMessage() {
        return new Queue(SUPPLIER_MATCH_MESSAGE);
    }


    @Bean
    public FanoutExchange newOrderExchange() {
        return new FanoutExchange(N_ORDER_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeOderMatchMessage(Queue oderMatchMessage, FanoutExchange newOrderExchange) {
        return BindingBuilder.bind(oderMatchMessage).to(newOrderExchange);
    }


    @Bean
    public FanoutExchange orderMatchChangeExchange() {
        return new FanoutExchange(ORDER_MATCH_CHANGE_EXCHANGE);
    }


    @Bean
    public Queue orderSupplierMatchNoticeMessage() {
        return new Queue(ORDER_S_MATCH_NOTICE_MESSAGE);
    }

    @Bean
    public Queue orderUserMatchNoticeMessage() {
        return new Queue(ORDER_U_MATCH_NOTICE_MESSAGE);
    }

    @Bean
    public Queue orderSupplierMatchCleanMessage() {
        return new Queue(ORDER_S_MATCH_NOTICE_CLEAN_MESSAGE);
    }

    @Bean
    Binding bindingExchangeOrderSupplierMatchNoticeMessage(Queue orderSupplierMatchNoticeMessage, FanoutExchange orderMatchChangeExchange) {
        return BindingBuilder.bind(orderSupplierMatchNoticeMessage).to(orderMatchChangeExchange);
    }

    @Bean
    Binding bindingExchangeOrderUserMatchNoticeMessage(Queue orderUserMatchNoticeMessage, FanoutExchange orderMatchChangeExchange) {
        return BindingBuilder.bind(orderUserMatchNoticeMessage).to(orderMatchChangeExchange);
    }

    @Bean
    Binding bindingExchangeOrderSupplierMatchCleanMessage(Queue orderSupplierMatchCleanMessage, FanoutExchange orderMatchChangeExchange) {
        return BindingBuilder.bind(orderSupplierMatchCleanMessage).to(orderMatchChangeExchange);
    }

    @Bean
    Binding bindingExchangeorderStatusChangeUser(Queue orderStatusChangeUser, FanoutExchange orderMatchChangeExchange) {
        return BindingBuilder.bind(orderStatusChangeUser).to(orderMatchChangeExchange);
    }

    @Bean
    FanoutExchange orderChangeExchange() {
        return new FanoutExchange(ORDER_CHANGE_EXCHANGE);
    }

    @Bean
    public Queue orderStatusChangeVendor() {
        return new Queue(ORDER_STATUS_CHANGE_VENDOR);
    }


    @Bean
    public Queue orderStatusChangeUser() {
        return new Queue(ORDER_STATUS_CHANGE_USER);
    }

    @Bean
    public Queue orderVendorNonfiction() {
        return new Queue(ORDER_VENDOR_NONFICTION);
    }

    @Bean
    Binding bindingOrderChangeExchangeorderVendorNonfiction(Queue orderVendorNonfiction, FanoutExchange orderChangeExchange) {
        return BindingBuilder.bind(orderVendorNonfiction).to(orderChangeExchange);
    }

    @Bean
    Binding bindingOrderChangeExchangeOrderStatusChangeVendor(Queue orderStatusChangeVendor, FanoutExchange orderChangeExchange) {
        return BindingBuilder.bind(orderStatusChangeVendor).to(orderChangeExchange);
    }

    @Bean
    Binding bindingOrderChangeExchangeOrderStatusChangeUser(Queue orderStatusChangeUser, FanoutExchange orderChangeExchange) {
        return BindingBuilder.bind(orderStatusChangeUser).to(orderChangeExchange);
    }

    /**
     * 订单延迟消息
     *
     * @return 订单状态
     */
    @Bean
    Queue orderDelayMessage() {
        return QueueBuilder.durable(ORDER_DELAY_MESSAGE)
                .withArgument("x-message-ttl", TimeUnit.HOURS.toMillis(1L))
                .withArgument("x-dead-letter-exchange", ORDER_MATCH_CHANGE_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingOderDelayWithNewOrderEx(Queue orderDelayMessage, FanoutExchange newOrderExchange) {
        return BindingBuilder.bind(orderDelayMessage).to(newOrderExchange);
    }

}
