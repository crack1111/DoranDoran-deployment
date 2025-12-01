package kr.ac.kopo.dorandoran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import kr.ac.kopo.dorandoran.model.Alert;

@Component
public class AlertBroadcaster {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendToUser(String memberId, Alert alert) {
        System.out.println("📡 [AlertBroadcaster] Sending alert to: " + memberId);
        messagingTemplate.convertAndSendToUser(
            memberId, "/queue/alerts", alert
        ); 
    }
}
