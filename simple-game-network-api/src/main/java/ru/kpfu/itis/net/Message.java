package ru.kpfu.itis.net;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Message {
    private byte type;
    private byte[] data;

    public Message(byte type) {
        this.type = type;
    }

    public Message(byte type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public byte getType() {
        return type;
    }
}
