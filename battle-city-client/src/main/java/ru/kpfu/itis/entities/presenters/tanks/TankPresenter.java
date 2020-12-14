package ru.kpfu.itis.entities.presenters.tanks;

import ru.kpfu.itis.entities.models.tanks.TankModel;
import ru.kpfu.itis.entities.presenters.Presenter;
import ru.kpfu.itis.entities.states.tanks.TankState;
import ru.kpfu.itis.entities.views.tanks.TankView;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.others.RouteMove;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class TankPresenter extends Presenter {
    private TankModel tankModel;
    private TankView tankView;

    public TankPresenter(TankModel tankModel, TankView tankView) {
        this.tankModel = tankModel;
        this.tankView = tankView;
    }

    public void move(float x, float y, RouteMove route) {
        tankView.animate(route);

        tankView.setTranslateX(x);
        tankView.setTranslateY(y);

        tankModel.move(x, y, route);
    }

    @Override
    public TankModel getModel() {
        return tankModel;
    }

    @Override
    public TankView getView() {
        return tankView;
    }

    @Override
    public void updateState(State state) {
        System.out.println(state + "-------");
        RouteMove route = RouteMove.getRouteMove(((TankState) state).getLastMove());
        move(state.getX(), state.getY(), route);

        tankModel.setState(state);
    }
}
