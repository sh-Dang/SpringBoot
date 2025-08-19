package com.sinse.chatroomapp.model.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.chatroomapp.config.HttpSessionConfigurator;
import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.model.dto.Room;
import com.sinse.chatroomapp.model.dto.RoomResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@ServerEndpoint(value="/chat/multi", configurator = HttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    //접속자보관=> 각 방마다 그룹화 이기 때문에 접속자 명단은 항상 필요
    //접속자 1당 Session 1이 생성되기 때문에 모든 접속자를 collection 프레임웍에 넣음
    private static Set<Session> userList=new HashSet<>();//서버측에서 필요한 접속자 정보
    //아니면 DTO를 사용하는것도 괜찮을듯
    //private static Set<*DTO*> memberList=new HashSet<>();//클라이언트에게 전달 할 접속자 정보
    private static Set<Member> memberList=new HashSet<>();//클라이언트에게 전달 할 접속자 정보
    private static Set<Room> roomList=new HashSet<>();//클라이언트에게 전달 할 접속자 정보
    private static ObjectMapper objectMapper = new ObjectMapper();


    //HttpSession을 지원하지 않기 때문에 다른 클래스의 도움을 받음
    //클래스 명 : config.HttpSessionConfigurator
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception {
        HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        log.debug("세션에 담긴 정보는 =======" + session.toString());
        log.debug("Http세션에 담긴 정보는 =======" + httpSession.toString());
        if(httpSession != null){
            log.debug("if문 진입*&*&*&*");
            //집중을 위한 session에 담긴변수와 도메인명 다르게 설정하기..!
            Member member=(Member)httpSession.getAttribute("member");

            //클라이언트 브라우저에서는 접속자 명단만 필요 => 필요한 정보만 추출해서 보관, 전달 해주기
            session.getUserProperties().put("member", member);
            session.getUserProperties().put("id",member.getId());
            //하나의 접속자를 현재 session에 추가.
            userList.add(session);
            log.debug("세션에 담긴 정보2222는 =======" + session.toString());
            //접속한 클라이언트가 알아야 할 정보 전송 (누가접속, 방들의 정보)
            //단 클라이언트와의 통신은 정해진 프로토콜을 지켜보내자
            /*
             *   {
             *       responseType:"createRoom",
             *       memberList : [
             *           {
             *               id:"mario",
             *               name:"말이오",
             *               email:"mario@naver.com"
             *           }
             *       ],
             *       roomList : [
             *           {
             *           }
             *       ]
             *   }
             * */
            //응답정보 만들기
            RoomResponse roomResponse=new RoomResponse();
            roomResponse.setResponseType("createRoom");

            //회원정보 채우기
            Member obj = new Member();
            obj.setId(member.getId());
            obj.setName(member.getName());
            obj.setEmail(member.getEmail());
            memberList.add(obj);

            roomResponse.setMemberList(memberList);
            String json = objectMapper.writeValueAsString(roomResponse); //java객체를 JSON으로 바꾸어버림
            session.getAsyncRemote().sendText(json);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.debug("onMessage = " + message);

        JsonNode jsonNode= objectMapper.readTree(message);
        String requestType = jsonNode.get("requestType").asText();
        if(requestType.equals("createRoom")){ //방 생성하기 요청이라면
            log.debug("creatRoom반환 받음 // 방만들기");
            String userId =  jsonNode.get("userId").asText();
            String roomName = jsonNode.get("roomName").asText();
            //로그인 시 사용된 HttpSession에 들어있는 정보, 웹소켓 회원정보를 비교하여
            //일치하는지 검증
            Member member = (Member)session.getUserProperties().get("member");
            if(!member.getId().equals(userId)){
                //클라이언트에게 에러를 전송
            }else{
                //방 고유식별자..1
                UUID uuid = UUID.randomUUID();
                Room room = new Room();
                room.setUUID(uuid.toString());
                room.setMaster(userId);
                room.setRoomName(roomName);

                //참여자 목록
                Set users = new HashSet();
                Member obj = new Member();
                obj.setId(member.getId());
                obj.setName(member.getName());
                obj.setEmail(member.getEmail());
                users.add(obj);//방을 개설한 주인을 참여자로 등록
                room.setUsers(users);

                roomList.add(room);
                /*
                * 클라이언트에게 전송할 응답 프로토콜
                * {
                *       responseType:"createRoom",
                *       memberList:[
                *           {
                *           }
                *       ]
                *       roomList:[
                *           {
                *               UUID : "dfdsinoiqfwe",
                *               master:"mario",
                *           }
                *       ]
                * }
                * */
                RoomResponse roomResponse=new RoomResponse();
                roomResponse.setResponseType("createRoom");
                roomResponse.setMemberList(memberList);
                roomResponse.setRoomList(roomList);

                session.getAsyncRemote().sendText(objectMapper.writeValueAsString(roomResponse));

            }
        }else if(requestType.equals("joinRoom")){

        }else if(requestType.equals("leaveRoom")){

        }else if(requestType.equals("sendMessage")){

        }
    }
}
