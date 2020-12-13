package ru.kpfu.itis.entities.models.bullets;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.entities.states.bullets.BulletState;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.others.Position;
import ru.kpfu.itis.others.GameProperties;
import ru.kpfu.itis.utils.Move;
import ru.kpfu.itis.others.RouteMove;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BulletModel extends Model {
    private boolean isAlive = true;
    private BulletState state;
    private RouteMove route;
    private byte speed;

    public BulletModel(float x, float y, byte speed, RouteMove route) {
        super(EntityType.BULLET, x, y, GameProperties.BULLET_WIDTH, GameProperties.BULLET_HEIGHT);
        this.state = new BulletState(speed);
        this.speed = speed;
        this.route = route;
    }

    public void move() {
        Position position = Move.getNewPositionByRoute(new Position(x, y), route, speed);
        setX(position.getX());
        setY(position.getY());
    }

    @Override
    public State getState() {
        state.setSpeed(speed);
        state.setRoute(route.getByte());
        state.setX(x);
        state.setY(y);

        return state;
    }

    @Override
    public void setState(State state) {
        this.state = (BulletState) state;

        route = RouteMove.getRouteMove(this.state.getRoute());
        speed = this.state.getSpeed();
        x = this.state.getX();
        y = this.state.getY();
    }

    public RouteMove getRoute() {
        return route;
    }

    public void destroy() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }
}
