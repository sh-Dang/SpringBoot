package com.sinse.chatroomapp.model.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.chatroomapp.config.HttpSessionConfigurator;
import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.model.dto.*;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@ServerEndpoint(value = "/chat/multi", configurator = HttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    //접속자보관=> 각 방마다 그룹화 이기 때문에 접속자 명단은 항상 필요
    //접속자 1당 Session 1이 생성되기 때문에 모든 접속자를 collection 프레임웍에 넣음
    private static Set<Session> userList = new HashSet<>();//서버측에서 필요한 접속자 정보
    //아니면 DTO를 사용하는것도 괜찮을듯
    //private static Set<*DTO*> memberList=new HashSet<>();//클라이언트에게 전달 할 접속자 정보
    private static Set<Member> memberList = new HashSet<>();//클라이언트에게 전달 할 접속자 정보
    private static Set<Room> roomList = new HashSet<>();//클라이언트에게 전달 할 접속자 정보
    private static ObjectMapper objectMapper = new ObjectMapper();


    //HttpSession을 지원하지 않기 때문에 다른 클래스의 도움을 받음
    //클래스 명 : config.HttpSessionConfigurator
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        log.debug("세션에 담긴 정보는 =======" + session.toString());
        log.debug("Http세션에 담긴 정보는 =======" + httpSession.toString());
        if (httpSession != null) {
            log.debug("if문 진입*&*&*&*");
            //집중을 위한 session에 담긴변수와 도메인명 다르게 설정하기..!
            Member member = (Member) httpSession.getAttribute("member");

            //클라이언트 브라우저에서는 접속자 명단만 필요 => 필요한 정보만 추출해서 보관, 전달 해주기
            session.getUserProperties().put("member", member);
            session.getUserProperties().put("id", member.getId());
            //하나의 접속자를 현재 session에 추가.
            userList.add(session);
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
            CreateRoomResponse roomResponse = new CreateRoomResponse();
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

        JsonNode jsonNode = objectMapper.readTree(message);
        String requestType = jsonNode.get("requestType").asText();
        if (requestType.equals("createRoom")) { //방 생성하기 요청이라면
            log.debug("**requestType = " + requestType + "반환 받음 // 방만들기");
            String userId = jsonNode.get("userId").asText();
            String roomName = jsonNode.get("roomName").asText();
            //로그인 시 사용된 HttpSession에 들어있는 정보, 웹소켓 회원정보를 비교하여
            //일치하는지 검증
            Member member = (Member) session.getUserProperties().get("member");
            if (!member.getId().equals(userId)) {
                //클라이언트에게 에러를 전송
            } else {
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
                room.setUserList(users);

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
                CreateRoomResponse roomResponse = new CreateRoomResponse();
                roomResponse.setResponseType("createRoom");
                roomResponse.setMemberList(memberList);
                roomResponse.setRoomList(roomList);

                //방이 생성된 사실을 서버에 접속한모든 클라이언트가 알아야 하므로
                //userList의 수만큼 반복문..(브로드캐스팅의 대상이 됨)
                for(Session ss : userList) {
                    ss.getAsyncRemote().sendText(objectMapper.writeValueAsString(roomResponse));
                }

            }
        } else if (requestType.equals("enterRoom")) {
            log.debug("**requestType = " + requestType + " 반환 받음 // 방 입장하기");
            /*
                1) 요청한 클라이언트를 선택한 방에 참여시키기
                    - 클라이언트가 전송한 uuid를 이용해 방 탐색
                    - 해당 Room이 보유한 Set에 클라이언트를 참여자로 등록
                    - 중복 등록은 방지해야 함
             */
            String uuid = jsonNode.get("uuid").asText();

            // [명령형 프로그래밍] 전통적인 방식: for문으로 직접 탐색
            Room room = null;
            for (Room roooom : roomList) {
                if (uuid.equals(roooom.getUUID())) { // 일치하는 방 발견 시
                    room = roooom;
                    break;
                }
            }

            // [선언형 프로그래밍] Stream API로 조건만 선언
//            roomList.stream()
//                    .filter(roooom -> uuid.equals(roooom.getUUID())) // 조건에 맞는 방만 필터링
//                    .findFirst() // 첫 번째 결과 반환
//                    .orElse(null);

            //찾아낸 Room 안에 채팅 참여자로 등록(중복은 방지)
            //session에 담긴 회원정보(로그인 회원 이라고 보면 됨)
            Member member = (Member)session.getUserProperties().get("member");

            //룸에 들어있는 유저들 정보와 비교하여 같지 않은 경우에만 유저를 방에 추가
            boolean exists = false;
            for(Member obj : room.getUserList()) {
                if(member.getId().equals(obj.getId())) {
                    exists = true;//중복 발견
                    break;
                }
            }
            //Room에 등록되어 있지 않다면..
            Member obj = null;// 클라이언트에게 전송될 객체이므로 필요한 정보만 보내주기
            if(exists==false) {
                obj = new Member();
                obj.setId(member.getId());
                obj.setName(member.getName());
                obj.setEmail(member.getEmail());
                room.getUserList().add(obj);
            }
            /*
                {
                    responseType:"enterRoom",
                    room:{

                    }
                }
            * */

            //응답정보 만들기
            EnterRoomResponse enterRoomResponse = new EnterRoomResponse();
            enterRoomResponse.setResponseType("enterRoom");
            enterRoomResponse.setRoom(room);

            session.getAsyncRemote().sendText(objectMapper.writeValueAsString(enterRoomResponse));
        } else if (requestType.equals("sendMessage")) {
            log.debug("**requestType = " + requestType + " 반환 받음 // 메시지 보내기");

            String sender = jsonNode.get("sender").asText();
            String data = jsonNode.get("data").asText();
            String uuid = jsonNode.get("uuid").asText();
            /*
                1) 같은 방에있는 유저들에게 브로드캐스팅
                    - 클라이언트가 전송한 UUID를 이용하여, 서버에 존재하는 여러 채팅방 중 한 방을 선택
            * */
            Room room = null;
            for(Room roooom:roomList){
                if(uuid.equals(roooom.getUUID())) {
                    room=roooom;
                    break;
                }
            }

            //Room에 들어있는 대화참여자들의 정보를 이용하여, Session을 보유한 userList와 비교
            //대화 참여자의 Session이 발견되면, 메시지를 보내자(브로드 캐스팅)
            /*
                {
                    responseType:"sendMessage",
                    sender:"",
                    data:"",
                    uuid:""
                }
            * */
            //전송 메시지 구성
            SendMessageResponse sendMessageResponse = new SendMessageResponse();
            sendMessageResponse.setResponseType("sendMessage");
            sendMessageResponse.setSender(sender);
            sendMessageResponse.setData(data);
            sendMessageResponse.setUuid(uuid);
            String json = objectMapper.writeValueAsString(sendMessageResponse);




            for(Session ss : userList){//전체 접속자를 대상으로
                //지정된 방에 참여한 유저들을 대상으로
                for(Member member : room.getUserList()){
                    //웹 소켓 Session에 심어놓은 Member를 꺼내자(비교를 위해)
                    Member obj = (Member)ss.getUserProperties().get("member");
                    if(obj.getId().equals(member.getId())) {
                        ss.getAsyncRemote().sendText(json);
                    }
                }
            }

        } else if (requestType.equals("leaveRoom")) {
            log.debug("**requestType = " + requestType + " 반환 받음 // 방 나가기");
        }
    }
    @OnClose
    public void onClose(Session session) throws Exception{
        /****************************************************************************
         * 죽는 부분은 다른 남은 사람들을 대상으로 브로드캐스팅 해야하기 때문에 나중에 할 것
         * **************************************************************************/

        Member member = (Member) session.getUserProperties().get("member");
        Room room = null;
        Member roomMember = null;
        for(Room roooom : roomList) {
            for(Member obj:roooom.getUserList()){
                if(obj.getId().equals(member.getId())) {//머무는 방이 발견되면
                    room=roooom;
                    roomMember=obj;
                    break;
                }
            }
        }
        room.getUserList().remove(roomMember);
        CloseResponse closeResponse = new CloseResponse();
        closeResponse.setResponseType("close");
        closeResponse.setMemberList(memberList);
        closeResponse.setRoomList(roomList);

        String json = objectMapper.writeValueAsString(closeResponse);
        session.getAsyncRemote().sendText(json);
        //이러면 Session의 로그인할때 썼던 멤버도 제거되니..? GPT야 이거 대답해줘
        //memberList에서 제거
        memberList.remove(member);
        //3가지 목록에서 사용자 제거
        userList.remove(session);

        //현재 접속자가 참여하고 있었던 그 방에서 빼야함

    }
}
