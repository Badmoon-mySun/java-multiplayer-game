package ru.kpfu.itis.entities.models;

import ru.kpfu.itis.entities.states.State;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class Model {
    protected double width, height;
    protected float x, y;
    protected byte type;

    public Model(byte type, float x, float y, double width, double height) {
        this.type = type;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public abstract State getState();

    public abstract void setState(State state);

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public byte getType() {
        return type;
    }
}
