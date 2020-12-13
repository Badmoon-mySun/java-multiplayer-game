package ru.kpfu.itis.entities.models.tanks;

import ru.kpfu.itis.entities.models.Destroyable;
import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.entities.states.tanks.TankState;
import ru.kpfu.itis.others.GameProperties;
import ru.kpfu.itis.others.RouteMove;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class TankModel extends Model implements Destroyable {
    private RouteMove lastMove;
    private byte healthPoint;
    private TankState state;
    protected byte speed;

    public TankModel(byte tankType, float x, float y) {
        this(tankType, x, y, (byte) 2, (byte) 1);
    }

    public TankModel(byte tankType, float x, float y, byte speed, byte hp) {
        this(tankType, x, y, speed, hp, RouteMove.UP);
    }

    public TankModel(byte tankType, float x, float y, byte speed, byte hp, RouteMove lastMove) {
        super(tankType, x, y, GameProperties.BLOCK_WIDTH, GameProperties.BLOCK_HEIGHT);

        state = new TankState(tankType);
        this.lastMove = lastMove;
        this.healthPoint = hp;
        this.speed = speed;
    }

    public void move(float x, float y, RouteMove route) {
        this.lastMove = route;
        setX(x);
        setY(y);
    }

    @Override
    public State getState() {
        state.setHP(healthPoint);
        state.setLastMove(lastMove.getByte());
        state.setX(getX());
        state.setY(getY());

        return state;
    }

    @Override
    public void setState(State state) {
        this.state = (TankState) state;

        this.lastMove = RouteMove.getRouteMove(this.state.getLastMove());
        setHP(this.state.getHP());
        setX(state.getX());
        setY(state.getY());
    }

    public void hit() {
        healthPoint -= healthPoint > 0 ? 1 : 0;
    }

    public boolean isALive() {
        return healthPoint > 0;
    }

    public byte getSpeed() {
        return speed;
    }

    public byte getHP() {
        return healthPoint;
    }

    public void setHP(byte healthPoint) {
        this.healthPoint = healthPoint;
    }

    public RouteMove getLastMove() {
        return lastMove;
    }

    public void setLastMove(RouteMove lastMove) {
        this.lastMove = lastMove;
    }
}
