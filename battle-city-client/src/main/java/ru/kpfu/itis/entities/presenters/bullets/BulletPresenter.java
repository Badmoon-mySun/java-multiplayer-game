package ru.kpfu.itis.entities.presenters.bullets;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.models.bullets.BulletModel;
import ru.kpfu.itis.entities.presenters.Presenter;
import ru.kpfu.itis.entities.views.SpriteView;
import ru.kpfu.itis.entities.views.bullets.BulletView;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.others.RouteMove;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BulletPresenter extends Presenter {
    private BulletModel model;
    private BulletView view;

    public BulletPresenter(BulletModel model, BulletView view, RouteMove route) {
        this.model = model;
        this.view = view;

        view.setTranslateX(model.getX());
        view.setTranslateY(model.getY());
    }

    public void move() {
        model.move();

        view.setTranslateX(model.getX());
        view.setTranslateY(model.getY());
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public SpriteView getView() {
        return view;
    }

    @Override
    public void updateState(State state) {
        model.setState(state);

        view.setTranslateX(state.getX());
        view.setTranslateY(state.getY());
    }
}
