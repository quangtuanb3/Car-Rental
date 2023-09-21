package com.example.case_study_car.controller;

import com.example.case_study_car.Socket.Notification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessageTemplate;
    @MessageMapping("/message")
    @SendTo("/message/public")
    public Notification receivePublicMessage(@Payload Notification notification) {
        return notification;
    }


    @MessageMapping("/private-message")
    @SendTo("/admin/public")
    public Notification receivePrivateMessage(@Payload Notification notification) {
        simpMessageTemplate.convertAndSendToUser(notification.getReceiverName(),"/private", notification);
        return notification;
    }

}
