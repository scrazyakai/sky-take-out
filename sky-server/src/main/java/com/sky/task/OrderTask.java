package com.sky.task;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ?")
    public void proceedTimeOutOrder(){
    log.info("自动处理超时订单:{}",LocalDateTime.now());
    LocalDateTime now = LocalDateTime.now();
    now.plusMinutes(-15);
    List<Orders> orders = orderMapper.getByStatusAndDateTimeLt(Orders.PENDING_PAYMENT,now);
    if(orders != null&&orders.size()>0){
        for(Orders order:orders){
            order.setStatus(Orders.CANCELLED);
            order.setCancelTime(LocalDateTime.now());
            order.setCancelReason("订单超时,自动取消");
            orderMapper.update(order);
        }
    }

    }

    /**
     * 处理处于派送中的订单
     */
    //每天晚上1点将订单完成
    @Scheduled(cron = "0 0 1 * * ?")
    public void proceedDeliveryOrder(){
    log.info("定时处理处于派送中的订单:{}",LocalDateTime.now());
    LocalDateTime time = LocalDateTime.now();
    time.plusHours(-1);
    List<Orders> orders = orderMapper.getByStatusAndDateTimeLt(Orders.DELIVERY_IN_PROGRESS,time);
    if(orders != null&&orders.size()>0){
        for(Orders order:orders){
            order.setStatus(Orders.COMPLETED);
            orderMapper.update(order);
        }
    }
    }
}
