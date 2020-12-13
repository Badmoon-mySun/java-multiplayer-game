package ru.kpfu.itis.entities.views.tanks;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class SimpleTankView extends TankView {

    public SimpleTankView(Image image, int xIndex, int yIndex) {
        super(image, xIndex, yIndex);
    }

    @Override
    protected void setNextFrame() {
        if (lastFrame < columns - 1) {
            lastFrame += 1;
        } else {
            lastFrame = 0;
        }

        double x = (xIndex + lastFrame) * width;

        imageView.setViewport(new Rectangle2D(x, yIndex * height- 1, width, height));
    }
}
