package ru.kpfu.itis.utils;

import javafx.concurrent.Task;
import ru.kpfu.itis.controllers.GameController;
import ru.kpfu.itis.entities.states.blocks.BlockState;
import ru.kpfu.itis.entities.states.bullets.BulletState;
import ru.kpfu.itis.entities.states.tanks.TankState;
import ru.kpfu.itis.net.Connection;
import ru.kpfu.itis.net.ConnectionListener;
import ru.kpfu.itis.net.Message;
import ru.kpfu.itis.net.MessageType;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.others.RouteMove;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class ServerConnection implements ConnectionListener {
    private List<State> lastStates = new ArrayList<>();
    private Task<Void> taskOnGameStart;
    private byte clientTankType = -1;
    private Connection connection;
    private String roomKey = "";

    public ServerConnection(String ip, int port) throws IOException {
        this.connection = new Connection(this, ip, port);
    }

    public void sendPlayerMove(float x, float y, RouteMove route) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(ByteBuffer.allocate(4).putFloat(x).array());
            out.write(ByteBuffer.allocate(4).putFloat(y).array());
            out.write(route.getByte());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.sendMessage(new Message(MessageType.PLAYER_MOVE.getByte(), out.toByteArray()));
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendShot() {
        connection.sendMessage(new Message(MessageType.PLAYER_SHOT.getByte(), new byte[]{}));
    }

    public void sendRoomSettings(byte plCount, byte stage) {
        connection.sendMessage(new Message(MessageType.ROOM_SETTINGS.getByte(), new byte[]{plCount, stage}));
    }

    private void readSnapshots(byte[] data) {

        byte b;
        State state;
        List<State> stateList = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(data))) {
            while (in.available() > 0) {
                b = in.readByte();

                if (b == 3) { //TODO
                    System.out.println(b);
                    continue;
                }

                switch (b) {
                    case EntityType.BRICK_BLOCK:
                    case EntityType.LASTING_BLOCK:
                        state = new BlockState(b);
                        break;
                    case EntityType.PLAYER_1:
                    case EntityType.PLAYER_2:
                        state = new TankState(b);
                        break;
                    case EntityType.BULLET:
                        state = new BulletState(b);
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong entity type");
                }

                state.parseBinary(in);

                stateList.add(state);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        lastStates = stateList;
    }

    @Override
    public void onReceiveMessage(Connection connection, Message message) {
        try {
            switch (MessageType.getMessageType(message.getType())) {
                case SNAPSHOT:
                    readSnapshots(message.getData());
                    break;
                case CLIENT_TANK_TYPE:
                    clientTankType = message.getData()[0];
                    break;
                case ROOM_KEY:
                    roomKey = new String(message.getData());
                    break;
                case GAME_START:
                    if (taskOnGameStart != null) {
                        new Thread(taskOnGameStart).start();
                    }
            }
        } catch (Exception ignore) { }
    }

    public List<State> getStates() {
        return lastStates;
    }

    @Override
    public void onConnectionReady(Connection connection) {

    }

    @Override
    public void onDisconnect(Connection connection) {

    }

    @Override
    public void onException(Connection connection, Exception exp) {

    }

    public byte getClientTankType() {
        return clientTankType;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setTaskOnGameStart(Task<Void> taskOnGameStart) {
        this.taskOnGameStart = taskOnGameStart;
    }
}
