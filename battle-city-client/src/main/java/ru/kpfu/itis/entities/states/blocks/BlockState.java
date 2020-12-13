package ru.kpfu.itis.entities.states.blocks;

import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.utils.StreamUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BlockState extends State {
    private short status;

    public BlockState(byte entityType) {
        this(entityType, 0, 0, (short) -1);
    }

    public BlockState(byte entityType, float x, float y, short status) {
        super(entityType);
        this.status = status;
        this.x = x;
        this.y = y;
    }

    @Override
    public void parseBinary(DataInputStream stream) {
        id = StreamUtils.readShort(stream);
        status = StreamUtils.readShort(stream);
        x = StreamUtils.readFloat(stream);
        y = StreamUtils.readFloat(stream);
    }

    @Override
    public void fillBinary(DataOutputStream stream) {
        StreamUtils.writeByte(stream, type);
        StreamUtils.writeShort(stream, id);
        StreamUtils.writeShort(stream, status);
        StreamUtils.writeFloat(stream, x);
        StreamUtils.writeFloat(stream, y);
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlockState that = (BlockState) o;
        return status == that.status;
    }

    @Override
    public String toString() {
        return "BlockState{" +
                "status=" + status +
                ", id=" + id +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
