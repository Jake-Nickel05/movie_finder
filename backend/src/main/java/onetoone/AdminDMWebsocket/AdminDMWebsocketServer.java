package onetoone.AdminDMWebsocket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/AdminDM/{username}")
@Component
public class AdminDMWebsocketServer {
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();
    private final Logger logger = LoggerFactory.getLogger(AdminDMWebsocketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        logger.info("[onOpen] " + username);

        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        }
        else {
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);

            sendMessageToParticularUser(username, "Welcome to Admin DM, "+username);
            broadcast("User: " + username + " has Joined the Chat");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        String username = sessionUsernameMap.get(session);

        logger.info("[onMessage] " + username + ": " + message);

        if (message.startsWith("@")) {
            String[] split_msg =  message.split("\\s+");
            StringBuilder actualMessageBuilder = new StringBuilder();
            for (int i = 1; i < split_msg.length; i++) {
                actualMessageBuilder.append(split_msg[i]).append(" ");
            }
            String destUserName = split_msg[0].substring(1);
            String actualMessage = actualMessageBuilder.toString();
            sendMessageToParticularUser(destUserName, "[DM from " + username + "]: " + actualMessage);
            sendMessageToParticularUser(username, "[DM from " + username + "]: " + actualMessage);
        }
        else {
            broadcast(username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);

        logger.info("[onClose] " + username);

        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        broadcast(username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = sessionUsernameMap.get(session);

        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

}
