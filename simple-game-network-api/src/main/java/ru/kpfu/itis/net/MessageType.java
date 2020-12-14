package ru.kpfu.itis.net;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public enum MessageType {
    PLAYER_MOVE(1),
    PLAYER_SHOT(2),
    SNAPSHOT(3),
    CLIENT_TANK_TYPE(4),
    GAME_START(5),
    GAME_OVER(6),
    ROOM_SETTINGS(7),
    ROOM_KEY(8),
    CREATE_ROOM(9),
    CONNECT_TO_ROOM(10);

    private int b;

    MessageType(int b) {
        this.b = b;
    }

    public byte getByte() {
        return (byte) b;
    }

    private static final Map<Byte, MessageType> nameMap;
    static {

        nameMap = new HashMap<>(MessageType.values().length);
        for (MessageType mt : MessageType.values()) {
            nameMap.put(mt.getByte(), mt);
        }
    }

    public static MessageType getMessageType(byte value) {
        return nameMap.get(value);
    }
}
