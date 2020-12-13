package ru.kpfu.itis.entities.states.tanks;

import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.utils.StreamUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class TankState extends State {
    private byte healthPoint;
    protected byte lastMove;

    public TankState(byte entityType) {
        this(entityType, 0, 0, (byte) 1);
    }

    public TankState(byte entityType, float x, float y, byte hp) {
        super(entityType);
        this.healthPoint = hp;
        this.x = x;
        this.y = y;
    }

    @Override
    public void parseBinary(DataInputStream stream) {
        id = StreamUtils.readShort(stream);
        healthPoint = StreamUtils.readByte(stream);
        lastMove = StreamUtils.readByte(stream);
        x = StreamUtils.readFloat(stream);
        y = StreamUtils.readFloat(stream);
    }

    @Override
    public void fillBinary(DataOutputStream stream) {
        StreamUtils.writeByte(stream, type);
        StreamUtils.writeShort(stream, id);
        StreamUtils.writeByte(stream, healthPoint);
        StreamUtils.writeByte(stream, lastMove);
        StreamUtils.writeFloat(stream, x);
        StreamUtils.writeFloat(stream, y);
    }

    public void setHP(byte healthPoint) {
        this.healthPoint = healthPoint;
    }

    public byte getHP() {
        return healthPoint;
    }

    public byte getLastMove() {
        return lastMove;
    }

    public void setLastMove(byte lastMove) {
        this.lastMove = lastMove;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TankState tankState = (TankState) o;
        return healthPoint == tankState.healthPoint &&
                lastMove == tankState.lastMove;
    }

    @Override
    public String toString() {
        return "TankState{" +
                "healthPoint=" + healthPoint +
                ", lastMove=" + lastMove +
                ", id=" + id +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
