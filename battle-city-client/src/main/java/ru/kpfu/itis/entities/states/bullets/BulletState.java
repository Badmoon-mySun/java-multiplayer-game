package ru.kpfu.itis.entities.states.bullets;

import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.utils.StreamUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BulletState extends State {
    private byte speed;
    private byte route;

    public BulletState(byte speed) {
        this(0, 0, speed);
    }

    public BulletState(float x, float y, byte speed) {
        super(EntityType.BULLET);
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    @Override
    public void parseBinary(DataInputStream stream) {
        id = StreamUtils.readShort(stream);
        route = StreamUtils.readByte(stream);
        x = StreamUtils.readFloat(stream);
        y = StreamUtils.readFloat(stream);
        speed = StreamUtils.readByte(stream);
    }

    @Override
    public void fillBinary(DataOutputStream stream) {
        StreamUtils.writeByte(stream, type);
        StreamUtils.writeShort(stream, id);
        StreamUtils.writeByte(stream, route);
        StreamUtils.writeFloat(stream, x);
        StreamUtils.writeFloat(stream, y);
        StreamUtils.writeByte(stream, speed);
    }

    public byte getSpeed() {
        return speed;
    }

    public void setSpeed(byte speed) {
        this.speed = speed;
    }

    public byte getRoute() {
        return route;
    }

    public void setRoute(byte route) {
        this.route = route;
    }
}
