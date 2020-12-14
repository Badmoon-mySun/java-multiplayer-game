package ru.kpfu.itis.server.levels;

import ru.kpfu.itis.entities.models.blocks.BlockBuilder;
import ru.kpfu.itis.entities.models.blocks.BlockModel;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class LevelBlockBuilder {

    public static BlockModel getBlock(byte sign, float x, float y) {
        for (LevelBlockEnum blockEnum : LevelBlockEnum.values()) {
            if (sign == blockEnum.getSign()) {
                BlockModel model = BlockBuilder.getBlockModel(blockEnum.getEntityType(), x, y, (short) -1);
                model.updateStatus(blockEnum.getStatus());
                return model;
            }
        }

        throw new IllegalArgumentException("Wrong level block sign");
    }
}
