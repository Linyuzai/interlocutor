package eason.linyuzai.interlocutor.controller;

import eason.linyuzai.interlocutor.InterlocutorTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class InterlocutorController {

    @MessageMapping(InterlocutorTopic.CHAT)
    void chat() {

    }
}
