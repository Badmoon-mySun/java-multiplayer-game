package ru.kpfu.itis.entities.presenters;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.views.SpriteView;
import ru.kpfu.itis.entities.states.State;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class Presenter {
    public abstract Model getModel();

    public abstract SpriteView getView();

    public abstract void updateState(State state);
}
