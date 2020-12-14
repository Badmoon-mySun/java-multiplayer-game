package ru.kpfu.itis.utils;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.models.blocks.BlockModel;
import ru.kpfu.itis.entities.models.bullets.BulletModel;
import ru.kpfu.itis.entities.models.tanks.TankModel;
import ru.kpfu.itis.entities.presenters.tanks.TankPresenter;
import ru.kpfu.itis.others.GameProperties;
import ru.kpfu.itis.others.Position;
import ru.kpfu.itis.others.RouteMove;

import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Move {

    public static Position getNewPositionByRoute(Position position, RouteMove route, float step) {
        switch (route) {
            case UP:
                return new Position(position.getX(), position.getY() - step);
            case DOWN:
                return new Position(position.getX(), position.getY() + step);
            case RIGHT:
                return new Position(position.getX() + step, position.getY());
            case LEFT:
                return new Position(position.getX() - step, position.getY());
            case STOP:
                return position;
            default:
                throw new IllegalArgumentException("wrong route - " + route);
        }
    }

    public static void moveTank(TankPresenter tankPresenter, List<Model> obstacles, RouteMove route) {
        Position position = getNewPossibleTankPosition(tankPresenter.getModel(), obstacles, route);
        tankPresenter.move(position.getX(), position.getY(), route);
        System.out.println(position);
    }

    public static void moveTank(TankModel tank, List<Model> obstacles, RouteMove route) {
        tankSetPosition(tank, getNewPossibleTankPosition(tank, obstacles, route));
    }

    private static Position getNewPossibleTankPosition(TankModel tank, List<Model> obstacles, RouteMove route) {
        correctionTank(tank, route);

        Position lastPosition = new Position(tank.getX(), tank.getY());

        if (route.equals(RouteMove.STOP)) {
            return lastPosition;
        }

        Position newPosition = getNewPositionByRoute(lastPosition, route, tank.getSpeed());

        tankSetPosition(tank, newPosition);

        if (!checkOutOfBoundMap(tank)) {
            tankSetPosition(tank, lastPosition);
            return lastPosition;
        }

        for (Model obs : obstacles) {
            if (Rect.rectCollision(tank, obs)) {
                tankSetPosition(tank, lastPosition);
                return lastPosition;
            }
        }

        tankSetPosition(tank, lastPosition);

        return newPosition;
    }

    private static void tankSetPosition(TankModel tank, Position position) {
        tank.setX(position.getX());
        tank.setY(position.getY());
    }

    private static void correctionTank(TankModel tank, RouteMove route) {
        double dif;

        if (route.equals(RouteMove.UP) || route.equals(RouteMove.DOWN)) {
            float x = tank.getX();
            dif = tank.getX() % (GameProperties.BLOCK_WIDTH / 2);
            if (dif > 0) {
                if (dif > GameProperties.BLOCK_WIDTH / 4) {
                    x += GameProperties.BLOCK_HEIGHT / 2 - dif;
                } else {
                    x -= dif;
                }
                tank.setX(x);
            }
        } else if (!route.equals(RouteMove.STOP)) {
            float y = tank.getY();
            dif = tank.getY() % (GameProperties.BLOCK_HEIGHT / 2);
            if (dif > 0) {
                if (dif > GameProperties.BLOCK_HEIGHT / 4) {
                    y += GameProperties.BLOCK_HEIGHT / 2 - dif;
                } else {
                    y -= dif;
                }
                tank.setY(y);
            }
        }
    }

    public static void bulletMove(BulletModel bulletModel, List<Model> obstacles) {
        bulletModel.move();

        if (!checkOutOfBoundMap(bulletModel)) {
            bulletModel.destroy();
            return;
        }

        for (Model model : obstacles) {
            if (Rect.rectCollision(model, bulletModel) && model != bulletModel) {
                bulletModel.destroy();

                if (model instanceof BlockModel) {
                    blockBulletHit((BlockModel) model, bulletModel.getRoute());
                } else if (model instanceof TankModel) {
                    TankModel tankModel = (TankModel) model;
                    tankModel.hit();
                    if (!tankModel.isALive()) {
                        tankModel.move(-100, -100, RouteMove.DOWN);
                    }
                } else if (model instanceof BulletModel) {
                    ((BulletModel) model).destroy();
                }
            }
        }
    }

    private static boolean checkOutOfBoundMap(Model model) {
        return model.getX() >= 0 && model.getY() >= 0
                && model.getX() + model.getWidth() <= GameProperties.BLOCK_WIDTH * GameProperties.WIDTH_BLOCK_COUNT
                && model.getY() + model.getHeight() <= GameProperties.BLOCK_HEIGHT * GameProperties.HEIGHT_BLOCK_COUNT;
    }

    private static void blockBulletHit(BlockModel blockModel, RouteMove bulletRoute) {
        switch (bulletRoute) {
            case RIGHT:
                for (int j = Short.BYTES * 2 - 1; j >= 0; j--) {
                    if ((blockModel.getStatus() & (4369 << j)) != 0) {
                        blockModel.updateStatus((short) (blockModel.getStatus() & ~(4369 << j)));
                        break;
                    }
                }
                break;
            case LEFT:
                for (int j = 0; j < Short.BYTES * 2; j++) {
                    if ((blockModel.getStatus() & (4369 << j)) != 0) {
                        blockModel.updateStatus((short) (blockModel.getStatus() & ~(4369 << j)));
                        break;
                    }
                }
                break;
            case DOWN:
                for (int j = Short.SIZE - 4; j >= 0; j -= 4) {
                    if ((blockModel.getStatus() & (15 << j)) != 0) {
                        blockModel.updateStatus((short) (blockModel.getStatus() & ~(15 << j)));
                        break;
                    }
                }
                break;
            default:
                for (int j = 0; j < Short.SIZE; j += 4) {
                    if ((blockModel.getStatus() & (15 << j)) != 0) {
                        blockModel.updateStatus((short) (blockModel.getStatus() & ~(15 << j)));
                        break;
                    }
                }
        }
    }
}
