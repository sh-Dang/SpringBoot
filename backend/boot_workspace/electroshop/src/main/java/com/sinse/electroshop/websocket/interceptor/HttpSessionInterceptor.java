package com.sinse.electroshop.websocket.interceptor;

import com.sinse.electroshop.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
/// Handshake과정에서 HttpSession 정보를 WebSocket 세션 속성으로 옮겨놓기 위한 객체
public class HttpSessionInterceptor implements HandshakeInterceptor {
    //WebSocket 핸드셰이크가 시작되기 전에 호출되는 메서드
    //이 타이밍을 놓치지 말고, HttpSession에 들어있는 값을 WebSocket의 Session에 옮겨 심을 것
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   /**WebSocketSession의 Attribute*/
                                   Map<String, Object> attributes) throws Exception {
        //HttpSession에서 Member꺼내기
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        /// 세션이 없는 경우 자동 인스턴스 생성을 막음(로그인을 하지 않았다는 뜻이니까)
        HttpSession httpSession = servletRequest.getServletRequest().getSession(false);

        if(httpSession != null){
            Member member = (Member)httpSession.getAttribute("member");
            //HttpSession에 있는걸 WebSocketSession으로 옮김
            attributes.put("member", member);
            log.debug("handshake시점에 추출한 회원의 이름은" + member.getId());
        }
        //true를 반환해야 handshake가 정상적으로 진행 됨->그럼 return조건으로 뭔가 제약을 걸수도 있을 듯
        return true;
    }

    //핸드셰이크가 끝난 후 호출되는 메서드
    //보통은 특별히 사용할 일이 없음 (로그기록정도)
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
