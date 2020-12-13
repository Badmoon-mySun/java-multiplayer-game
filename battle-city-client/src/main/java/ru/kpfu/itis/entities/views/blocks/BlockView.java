package ru.kpfu.itis.entities.views.blocks;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.entities.views.SpriteView;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class BlockView extends SpriteView {
    public BlockView(Image image) {
        super(new ImageView(image));
    }
}
