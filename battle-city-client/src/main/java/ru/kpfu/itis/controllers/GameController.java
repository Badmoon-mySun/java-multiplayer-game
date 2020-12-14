package ru.kpfu.itis.controllers;


import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.kpfu.itis.utils.ServerConnection;
import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.models.blocks.BrickBlockModel;
import ru.kpfu.itis.entities.models.blocks.LastingBlockModel;
import ru.kpfu.itis.entities.models.bullets.BulletModel;
import ru.kpfu.itis.entities.models.tanks.TankModel;
import ru.kpfu.itis.entities.presenters.*;
import ru.kpfu.itis.entities.presenters.blocks.BlockPresenter;
import ru.kpfu.itis.entities.presenters.bullets.BulletPresenter;
import ru.kpfu.itis.entities.presenters.tanks.TankPresenter;
import ru.kpfu.itis.entities.views.SpritesBuilder;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.entities.states.blocks.BlockState;
import ru.kpfu.itis.entities.states.bullets.BulletState;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.utils.Move;
import ru.kpfu.itis.utils.Rect;
import ru.kpfu.itis.others.RouteMove;

import java.util.*;
import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class GameController {
    private List<BulletPresenter> bullets = new ArrayList<>();
    private HashMap<Short, Presenter> map = new HashMap<>();
    private List<Model> obstacles = new ArrayList<>();

    private Map<KeyCode, Boolean> keys;
    private ServerConnection connection;
    private Pane pane = new Pane();

    private TankPresenter tank;

    private long lastShotTime = 0;
    private List<KeyCode> playerMovesKeys = new ArrayList<>(Arrays.asList(
            KeyCode.UP,
            KeyCode.DOWN,
            KeyCode.RIGHT,
            KeyCode.LEFT
    ));

    public GameController(ServerConnection connection, Map<KeyCode, Boolean> keys) {
        pane.setStyle("-fx-background-color: black");
        this.connection = connection;
        this.keys = keys;

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 28_000_000) {
                    updateMap(GameController.this.connection.getStates());
                    sendPlayerMove(now);
                    bulletMove();

                    lastUpdate = now ;
                }
            }
        };

        timer.start();
    }

    private void sendPlayerMove(long now) {
        for (KeyCode keyCode : playerMovesKeys) {
            if (keys.getOrDefault(keyCode, false)) {
                RouteMove routeMove = RouteMove.getRouteMove(keyCode.toString());

                Move.moveTank(tank, obstacles, routeMove);
                System.out.println(tank.getModel().getX());

                connection.sendPlayerMove(tank.getModel().getX(), tank.getModel().getY(), routeMove);
            }
        }

        long twoSeconds = 2_000_000_000;

        if (now - lastShotTime >= twoSeconds && keys.getOrDefault(KeyCode.SPACE, false)) {
            connection.sendShot();

            lastShotTime = now;
        }
    }

    private void bulletMove() {
        Iterator<BulletPresenter> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            BulletPresenter b = iterator.next();
            b.move();

            for (Presenter presenter : map.values()) {
                if (b.getModel() != presenter.getModel()
                        && Rect.rectCollision(b.getModel(), presenter.getModel())) {

                    iterator.remove();

                    map.remove(b.getModel().getState().getId());

                    pane.getChildren().remove(b.getView());
                    break;
                }
            }
        }
    }

    private Presenter getPresenterObjectByState(State state) {
        switch (state.getType()) {
            case EntityType.PLAYER_1:

                TankPresenter player1 = new TankPresenter(
                        new TankModel(state.getType(), state.getX(), state.getY()),
                        SpritesBuilder.getTankSprite(1));
                checkTankIsClient(player1);
                return player1;

            case EntityType.PLAYER_2:
                TankPresenter player2 = new TankPresenter(
                        new TankModel(state.getType(), state.getX(), state.getY()),
                        SpritesBuilder.getTankSprite(2));
                checkTankIsClient(player2);
                return player2;

            case EntityType.BRICK_BLOCK:
                BrickBlockModel blockModel = new BrickBlockModel((BlockState) state);
                obstacles.add(blockModel);
                return new BlockPresenter(blockModel, SpritesBuilder.getBlockView(state.getType()));

            case EntityType.LASTING_BLOCK:
                LastingBlockModel lastingModel = new LastingBlockModel((BlockState) state);
                obstacles.add(lastingModel);
                return new BlockPresenter(lastingModel, SpritesBuilder.getBlockView(state.getType()));

            case EntityType.BULLET:
                BulletState bulletState = (BulletState) state;
                RouteMove routeMove = RouteMove.getRouteMove(bulletState.getRoute());

                BulletModel bulletModel =
                        new BulletModel(state.getX(), state.getY(), bulletState.getSpeed(), routeMove);

                BulletPresenter bullet = new BulletPresenter(bulletModel,
                        SpritesBuilder.getBulletView(routeMove), routeMove);

                bullets.add(bullet);
                return bullet;

            default:
                throw new IllegalArgumentException("Unknown element type!");
        }
    }

    public void updateMap(List<State> states) {

        for (State state : states) {
            Presenter presenter = map.get(state.getId());

            if (presenter == null) {
                presenter = getPresenterObjectByState(state);

                pane.getChildren().add(presenter.getView());

                map.put(state.getId(), presenter);
            }

            if (!state.equals(presenter.getModel().getState())) {
                if (state.getType() != connection.getClientTankType()) {
                    presenter.updateState(state);
                }
            }
        }
    }

    private void checkTankIsClient(TankPresenter tank) {
        if (connection.getClientTankType() == tank.getModel().getType()) {
            this.tank = tank;
        } else {
            obstacles.add(tank.getModel());
        }
    }

    public Pane getPane() {
        return pane;
    }
}
