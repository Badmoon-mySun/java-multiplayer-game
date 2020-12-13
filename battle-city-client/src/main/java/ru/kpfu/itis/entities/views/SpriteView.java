package ru.kpfu.itis.entities.views;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class SpriteView extends Pane {
    protected ImageView imageView;

    public SpriteView(ImageView imageView) {
        this.imageView = imageView;
        getChildren().add(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
