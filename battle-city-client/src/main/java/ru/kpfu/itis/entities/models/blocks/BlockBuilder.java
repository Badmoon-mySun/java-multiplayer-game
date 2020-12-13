package ru.kpfu.itis.entities.models.blocks;

import ru.kpfu.itis.others.EntityType;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BlockBuilder {

    public static BlockModel getBlockModel(byte blockType, float x, float y, short status) {

        switch (blockType) {
            case EntityType.BRICK_BLOCK:
                return new BrickBlockModel(x, y, status);
            case EntityType.LASTING_BLOCK:
                return new LastingBlockModel(x, y, status);
            default:
                throw new IllegalArgumentException("Wrong block type");
        }
    }
}
