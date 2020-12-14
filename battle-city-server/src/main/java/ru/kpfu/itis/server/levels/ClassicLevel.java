package ru.kpfu.itis.server.levels;

import ru.kpfu.itis.entities.models.blocks.BlockModel;
import ru.kpfu.itis.others.GameProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class ClassicLevel extends Level {
    private List<BlockModel> blocks = new ArrayList<>();

    public ClassicLevel(byte num) {
        if (num == 1) {
            drawLevel(firstLevel());
        }
    }

    private void drawLevel(byte[][] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == '0') {
                    continue;
                }
                float y = (float) (i * GameProperties.BLOCK_HEIGHT);
                float x = (float) (j * GameProperties.BLOCK_WIDTH);

                blocks.add(LevelBlockBuilder.getBlock(array[i][j], x, y));
            }
        }
    }

    public List<BlockModel> getBlocks() {
        return blocks;
    }

    public static byte[][] firstLevel() {
        return new byte[][] {
                {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0'},
                {'0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0'},
                {'0', '1', '0', '1', '0', '1', '6', '1', '0', '1', '0', '1', '0'},
                {'0', '1', '0', '1', '0', '5', '0', '5', '0', '1', '0', '1', '0'},
                {'0', '5', '0', '5', '0', '3', '0', '3', '0', '5', '0', '5', '0'},
                {'3', '0', '3', '3', '0', '5', '0', '5', '0', '3', '3', '0', '3'},
                {'A', '0', '5', '5', '0', '3', '0', '3', '0', '5', '5', '0', 'A'},
                {'0', '3', '0', '3', '0', '1', '1', '1', '0', '3', '0', '3', '0'},
                {'0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0'},
                {'0', '1', '0', '1', '0', '5', '0', '5', '0', '1', '0', '1', '0'},
                {'0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'}
        };
    }
}
