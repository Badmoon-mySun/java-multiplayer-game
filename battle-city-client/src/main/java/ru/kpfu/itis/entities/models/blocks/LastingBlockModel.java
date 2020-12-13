package ru.kpfu.itis.entities.models.blocks;

import ru.kpfu.itis.entities.states.blocks.BlockState;
import ru.kpfu.itis.others.EntityType;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class LastingBlockModel extends BlockModel {

    public LastingBlockModel(float x, float y, short status) {
        super(EntityType.LASTING_BLOCK, x, y, status);
    }

    public LastingBlockModel(BlockState state) {
        super(state);
    }
}
