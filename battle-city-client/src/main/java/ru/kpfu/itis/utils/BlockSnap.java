package ru.kpfu.itis.utils;

import javafx.scene.shape.Rectangle;
import ru.kpfu.itis.entities.models.blocks.BlockModel;
import ru.kpfu.itis.entities.views.blocks.BlockView;
import ru.kpfu.itis.others.GameProperties;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BlockSnap {
    private static double subBlockHeight = GameProperties.BLOCK_HEIGHT / 4;
    private static double subBlockWidth = GameProperties.BLOCK_WIDTH / 4;

    public static void blockModelSnap(BlockModel model) {
        short status = model.getStatus();
        float x_cor = 0, y_cor = 0;

        for (int i = Short.BYTES - 1; i >= 0; i--) {
            if ((status & (255 << (i * 8))) != 0) {
                break;
            }

            y_cor += subBlockHeight * 2;
        }

        for (int i = Short.BYTES - 1; i >= 0; i--) {
            if ((status & (13107 << (i * 2))) != 0) {
                break;
            }

            x_cor += subBlockWidth * 2;
        }

        model.setWidth(getWidthByStatus(status));
        model.setHeight(getHeightByStatus(status));

        x_cor -= model.getX() % GameProperties.BLOCK_WIDTH;
        y_cor -= model.getY() % GameProperties.BLOCK_HEIGHT;

        model.setX(model.getX() + x_cor);
        model.setY(model.getY() + y_cor);
    }

    public static void blockViewSnap(BlockView view, short status) {

        for (int i = 0; i < Short.SIZE; i++) {
            if ((status & (1 << (15 - i))) == 0) {
                int x = i % 4;
                int y = i / 4;

                view.getChildren().add(new Rectangle(x * subBlockWidth,
                        y * subBlockHeight, subBlockWidth, subBlockHeight));
            }
        }
    }

    public static double getWidthByStatus(short status) {
        int subBlockCount = 1;

        for (int i = 0; i < GameProperties.BLOCK_WIDTH / subBlockWidth; i++) {
            if ((status & (4369 << i)) != 0) {
                subBlockCount++;
            }
        }

        subBlockCount -= subBlockCount % 2;

        return subBlockCount * subBlockWidth;
    }

    public static double getHeightByStatus(short status) {
        int subBlockCount = 1;

        for (int i = 0; i <= subBlockHeight; i += 4) {
            if ((status & (15 << i)) != 0) {
                subBlockCount++;
            }
        }

        subBlockCount -= subBlockCount % 2;

        return subBlockCount * subBlockHeight;
    }
}
