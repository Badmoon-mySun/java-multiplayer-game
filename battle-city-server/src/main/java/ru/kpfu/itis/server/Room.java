package ru.kpfu.itis.server;

import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.net.Connection;
import ru.kpfu.itis.net.ConnectionListener;
import ru.kpfu.itis.net.Message;
import ru.kpfu.itis.net.MessageType;
import ru.kpfu.itis.server.levels.ClassicLevel;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Room implements Runnable, ConnectionListener {
    private final Map<Connection, Byte> clientTankType = new HashMap<>();
    private byte playerCount = 2;
    private byte plByte = 1;
    private Game game;
    private byte stage;

    public Room() {

    }

    public Room(byte playerCount, byte stage) {
        this.playerCount = playerCount;
        this.stage = stage;
    }

    @Override
    public void run() {
        synchronized (clientTankType) {
            while (clientTankType.size() < playerCount) {
                try {
                    clientTankType.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        game = new Game(new ClassicLevel(stage));

        for (byte b : clientTankType.values()) {
            game.addPlayer(b);
        }

        sendToAllClients(new Message(MessageType.GAME_START.getByte(), new byte[]{}));

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(byteArrayOutputStream)) {

            while (game.alivePlayerCount() > 1) {
                game.update();

                Message message = new Message(MessageType.SNAPSHOT.getByte());

                List<State> stateList = game.getStates();

                for (State state : stateList) {
                    state.fillBinary(out);
                }

                message.setData(byteArrayOutputStream.toByteArray());

                byteArrayOutputStream.reset();

                sendToAllClients(message);

                Thread.sleep(50);
            }

            Thread.sleep(2000);

            sendToAllClients(new Message(MessageType.GAME_OVER.getByte(), new byte[]{}));
        } catch (InterruptedException ignore) {
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    public void addClient(Socket socket) {
        try {
            synchronized (clientTankType) {
                Connection connection = new Connection(this, socket);
                clientTankType.put(connection, plByte);
                clientTankType.notify();

                connection.sendMessage(new Message(MessageType.CLIENT_TANK_TYPE.getByte(), new byte[]{plByte}));

                plByte++;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendToAllClients(Message message) {
        for (Connection connection : clientTankType.keySet()) {
            connection.sendMessage(message);
        }
    }

    @Override
    public void onConnectionReady(Connection connection) {

    }

    @Override
    public void onReceiveMessage(Connection connection, Message message) {
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(message.getData()))) {
            switch (MessageType.getMessageType(message.getType())) {
                case PLAYER_MOVE:
                    game.movePlayer(clientTankType.get(connection), in.readFloat(), in.readFloat(), in.readByte());
                    break;
                case PLAYER_SHOT:
                    game.playerShot(clientTankType.get(connection));
                    break;
                case ROOM_SETTINGS:
                    this.playerCount = message.getData()[0];
                    this.stage = message.getData()[1];

                    new Thread(this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect(Connection connection) {

    }

    @Override
    public void onException(Connection connection, Exception exp) {

    }
}
