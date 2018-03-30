package eason.linyuzai.interlocutor;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class InterlocutorListener implements ApplicationListener<SessionSubscribeEvent> {

    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        //这里的sessionId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
        // String sessionId = headerAccessor.getSessionAttributes().get(Constants.SESSIONID).toString();

    }
}
