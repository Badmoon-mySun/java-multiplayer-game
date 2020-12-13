package ru.kpfu.itis.entities.states;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class State implements Serializable {
    protected short id = -1;
    protected byte type;
    protected float x, y;

    public State(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public abstract void parseBinary(DataInputStream stream);

    public abstract void fillBinary(DataOutputStream stream);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return id == state.id &&
                type == state.type &&
                Float.compare(state.x, x) == 0 &&
                Float.compare(state.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, x, y);
    }

    @Override
    public String toString() {
        return "type: " + type + ", id: " + id + ", x: " + x + ", y: " + y;
    }
}
