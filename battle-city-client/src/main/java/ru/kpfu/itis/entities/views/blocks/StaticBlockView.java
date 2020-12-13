package ru.kpfu.itis.entities.views.blocks;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.others.GameProperties;


/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class StaticBlockView extends BlockView {
    public StaticBlockView(Image image, int xIndex, int yIndex) {
        super(image);

        double width = GameProperties.BLOCK_WIDTH;
        double height = GameProperties.BLOCK_HEIGHT;
        imageView.setViewport(new Rectangle2D(xIndex * width, yIndex * height, width, height));
    }

    public ImageView getImageView() {
        return imageView;
    }
}
