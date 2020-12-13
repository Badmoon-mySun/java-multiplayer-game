package ru.kpfu.itis.entities.models.blocks;

import ru.kpfu.itis.entities.models.Model;
import ru.kpfu.itis.entities.states.State;
import ru.kpfu.itis.entities.states.blocks.BlockState;
import ru.kpfu.itis.utils.BlockSnap;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class BlockModel extends Model {
    protected short status;
    private BlockState state;

    public BlockModel(byte blockType, float x, float y, short status) {
        super(blockType, x, y, BlockSnap.getWidthByStatus(status), BlockSnap.getHeightByStatus(status));
        state = new BlockState(blockType);
        this.status = status;
    }

    public BlockModel(BlockState state) {
        this(state.getType(), state.getX(), state.getY(), state.getStatus());
    }

    @Override
    public State getState() {
        state.setStatus(status);
        state.setX(getX());
        state.setY(getY());

        return state;
    }

    @Override
    public void setState(State state) {
        this.state = (BlockState) state;
        setX(state.getX());
        setY(state.getY());

        updateStatus(this.state.getStatus());
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public short getStatus() {
        return status;
    }

    public void updateStatus(short status) {
        if (this.status != status) {
            this.status = status;

            System.out.println(status);
            BlockSnap.blockModelSnap(this);
        }
    }
}
