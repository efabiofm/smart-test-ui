package com.quenti.smarttestui.components;

import com.quenti.smarttestui.web.websocket.dto.TestDTO;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.annotation.PostConstruct;

/**
 * Created by juarez on 29/04/17.
 */
@Component
public class TestsComponent implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessageSendingOperations messagingTemplate;

    public TestsComponent(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendTests(){
        System.out.println("enviando");
//        messagingTemplate.convertAndSend("/topic/tests", new TestDTO("holaa"));
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {

    }
}
