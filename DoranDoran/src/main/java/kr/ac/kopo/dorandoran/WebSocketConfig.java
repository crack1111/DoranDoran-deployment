package kr.ac.kopo.dorandoran;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// ✅ enable /topic (broadcast) + /user (1:1 메시지)
		config.enableSimpleBroker("/topic", "/queue");

		// ✅ /app 으로 보내는 메시지만 @MessageMapping 처리
		config.setApplicationDestinationPrefixes("/app");

		// ✅ /user 접두어를 가진 메시지 처리 (convertAndSendToUser 에 필요)
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS(); // WebSocket 엔드포인트
	}

}
