package ru.kpfu.itis.server;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.models.bullets.BulletModel;
import ru.kpfu.itis.entities.models.tanks.TankModel;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.entities.states.tanks.TankState;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.server.levels.Level;
import ru.kpfu.itis.others.GameProperties;
import ru.kpfu.itis.utils.Move;
import ru.kpfu.itis.others.RouteMove;

import java.util.*;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Game {
    private Level level;
    private Map<Byte, TankModel> players = new HashMap<>();
    private final List<BulletModel> bullets = Collections.synchronizedList(new ArrayList<>());
    private final List<Model> models = new ArrayList<>();

    private short count = 1;

    public Game(Level level) {
        this.level = level;
        models.addAll(level.getBlocks());
    }

    public void addPlayer(byte type) {
        TankModel tank;

        if (type == EntityType.PLAYER_1) {
            tank = new TankModel(type, 0, 0);
        } else {
            tank = new TankModel(type, 576, 576);
        }

        players.put(type, tank);

        models.add(tank);
    }

    public void movePlayer(byte type, float x, float y, byte route) {
        TankModel tank = players.get(type);
        if (tank.isALive()) {
            tank.move(x, y, RouteMove.getRouteMove(route));
        }
    }

    public List<State> getStates() {
        update();

        List<State> states = new ArrayList<>();

        for (Model model : models) {
            State state = model.getState();

            if (state.getId() == -1) {
                state.setId(count);
                count++;
            }

            states.add(state);
        }

        return states;
    }

    public void playerShot(byte type) {
        TankModel tank = players.get(type);
        if (tank.isALive()) {
            BulletModel bulletModel = tankShot(tank);

            models.add(bulletModel);

            bullets.add(bulletModel);
        }
    }

    public void update() {
        synchronized (models) {
            Iterator<BulletModel> iterator = bullets.iterator();
            while (iterator.hasNext()) {
                BulletModel bul = iterator.next();

                Move.bulletMove(bul, models);

                if (!bul.isAlive()) {
                    iterator.remove();
                    models.remove(bul);
                }
            }
        }
    }

    public static BulletModel tankShot(TankModel tank) {
        double x, y;

        switch (tank.getLastMove()) {
            case UP:
                x = tank.getX() + (tank.getWidth() - GameProperties.BULLET_WIDTH) / 2;
                y = tank.getY() - GameProperties.BULLET_HEIGHT;
                break;
            case DOWN:
                x = tank.getX() + (tank.getWidth() - GameProperties.BULLET_WIDTH) / 2;
                y = tank.getY() + tank.getWidth();
                break;
            case LEFT:
                x = tank.getX() - GameProperties.BULLET_WIDTH;
                y = tank.getY() + (tank.getHeight() - GameProperties.BULLET_HEIGHT) / 2;
                break;
            default:
                x = tank.getX() + tank.getWidth();
                y = tank.getY() + (tank.getHeight() - GameProperties.BULLET_HEIGHT) / 2;
        }

        return new BulletModel((float) x, (float) y, tank.getSpeed(), tank.getLastMove());
    }

    public int alivePlayerCount() {
        int i = 0;
        for (TankModel tank : players.values()) {
            if (tank.isALive()) {
                i++;
            }
        }
        System.out.println(i);

        return i;
    }
}
