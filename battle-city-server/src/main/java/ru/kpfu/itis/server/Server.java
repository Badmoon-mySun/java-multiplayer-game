package ru.kpfu.itis.server;

import org.apache.commons.lang3.RandomStringUtils;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.net.Connection;
import ru.kpfu.itis.net.ConnectionListener;
import ru.kpfu.itis.net.Message;
import ru.kpfu.itis.net.MessageType;
import ru.kpfu.itis.server.levels.ClassicLevel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Server implements ConnectionListener {
    private Map<String, Room> rooms = new HashMap<>();
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        new Server(11905);
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        run();
    }

    private void run() {
        new Thread(() -> {
            while (true) {
                try {
                    new Connection(this, serverSocket.accept());
                } catch (IOException ignore) { }
            }
        }).start();
    }

    @Override
    public void onConnectionReady(Connection connection) {
    }

    @Override
    public void onReceiveMessage(Connection connection, Message message) {
        switch (MessageType.getMessageType(message.getType())) {
            case CREATE_ROOM:
                String key = generateRandomKey();
                connection.sendMessage(new Message(MessageType.ROOM_KEY.getByte(), key.getBytes()));
                connection.getThread().interrupt();

                Room room = new Room();
                room.addClient(connection.getSocket());

                rooms.put(key, room);
                System.out.println(key);
                break;
            case CONNECT_TO_ROOM:
                String connectKey = new String(message.getData());
                if (rooms.get(connectKey) != null) {
                    connection.getThread().interrupt();
                    System.out.println("connect key - " + connectKey);

                    rooms.get(connectKey).addClient(connection.getSocket());
                } else {
                    connection.disconnect();
                }
        }

    }

    @Override
    public void onDisconnect(Connection connection) {

    }

    @Override
    public void onException(Connection connection, Exception exp) {

    }

    public String generateRandomKey() {
        String generatedKey = RandomStringUtils.random(5, true, true);

        if (rooms.get(generatedKey) != null) {
            return generateRandomKey();
        }

        return generatedKey;
    }
}