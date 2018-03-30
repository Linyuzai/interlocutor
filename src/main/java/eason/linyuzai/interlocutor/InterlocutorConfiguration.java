package eason.linyuzai.interlocutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class InterlocutorConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/eason")
                .setHandshakeHandler(getInterlocutorHandshakeHandler())
                .addInterceptors(getInterlocutorHandshakeInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/user");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
        //StompHeaderAccessor
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new InboundChannelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        System.out.println("configureClientOutboundChannel");
    }

    @Bean
    HandshakeHandler getInterlocutorHandshakeHandler() {
        return new InterlocutorHandshakeHandler();
    }

    @Bean
    HandshakeInterceptor getInterlocutorHandshakeInterceptor() {
        return new InterlocutorHandshakeInterceptor();
    }

    public class InboundChannelInterceptor extends ChannelInterceptorAdapter {
        @Override
        public boolean preReceive(MessageChannel channel) {
            System.out.println("preReceive");
            return super.preReceive(channel);
        }

        @Override
        public Message<?> postReceive(Message<?> message, MessageChannel channel) {
            System.out.println("postReceive");
            return super.postReceive(message, channel);
        }

        @Override
        public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
            System.out.println("afterReceiveCompletion");
            super.afterReceiveCompletion(message, channel, ex);
        }

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            System.out.println("preSend");
            return super.preSend(message, channel);
        }

        @Override
        public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
            System.out.println("postSend");
            super.postSend(message, channel, sent);
        }

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
            System.out.println("afterSendCompletion");
            super.afterSendCompletion(message, channel, sent, ex);
        }
    }

    private class InterlocutorHandshakeHandler implements HandshakeHandler {

        @Override
        public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
            return true;
        }
    }

    private class InterlocutorHandshakeInterceptor implements HandshakeInterceptor {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            HttpSession session = getSession(request);
            if (session != null && session.getAttributeNames() != null) {
                System.out.println(session.getAttribute("uid"));
            } else {
                System.out.println("session null");
            }
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {

        }

        @Nullable
        private HttpSession getSession(ServerHttpRequest request) {
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
                //System.out.println(serverRequest.getServletRequest().getSession());
                return serverRequest.getServletRequest().getSession(false);
            }
            return null;
        }
    }
}
