package com.e3mall.admin.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//@RabbitListener(queues = "newItem")
//public class receiver {
//	@RabbitHandler
//    public void process(String id) {
//        System.out.println("Receiver : " + id);
//    }
//}
