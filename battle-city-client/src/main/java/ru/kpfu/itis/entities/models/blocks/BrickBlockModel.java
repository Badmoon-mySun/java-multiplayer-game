package ru.kpfu.itis.entities.models.blocks;

import ru.kpfu.itis.entities.models.Destroyable;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.entities.states.blocks.BlockState;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BrickBlockModel extends BlockModel implements Destroyable {

    public BrickBlockModel(float x, float y, short status) {
        super(EntityType.BRICK_BLOCK, x, y, status);
    }

    public BrickBlockModel(BlockState state) {
        super(state);
    }
}
