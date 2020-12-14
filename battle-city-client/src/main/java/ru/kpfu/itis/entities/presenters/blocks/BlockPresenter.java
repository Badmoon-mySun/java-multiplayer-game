package ru.kpfu.itis.entities.presenters.blocks;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.models.blocks.BlockModel;
import ru.kpfu.itis.entities.presenters.Presenter;
import ru.kpfu.itis.entities.views.SpriteView;
import ru.kpfu.itis.entities.views.blocks.BlockView;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.utils.BlockSnap;
import ru.kpfu.itis.others.GameProperties;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BlockPresenter extends Presenter {
    protected BlockModel blockModel;
    protected BlockView blockView;

    public BlockPresenter(BlockModel blockModel, BlockView blockView) {
        this.blockModel = blockModel;
        this.blockView = blockView;

        blockView.setTranslateX(blockModel.getX() - blockModel.getX() % GameProperties.BLOCK_WIDTH);
        blockView.setTranslateY(blockModel.getY() - blockModel.getY() % GameProperties.BLOCK_HEIGHT);

        BlockSnap.blockViewSnap(blockView, blockModel.getStatus());
    }

    @Override
    public Model getModel() {
        return blockModel;
    }

    @Override
    public SpriteView getView() {
        return blockView;
    }

    @Override
    public void updateState(State state) {
        blockModel.setState(state);

        BlockSnap.blockViewSnap(blockView, blockModel.getStatus());
    }
}
