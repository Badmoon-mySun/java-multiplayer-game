package ru.kpfu.itis.entities.views.bullets;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.entities.views.SpriteView;
import ru.kpfu.itis.others.GameProperties;
import ru.kpfu.itis.others.RouteMove;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BulletView extends SpriteView {
    public BulletView(Image image, RouteMove route) {
        super(new ImageView(image));

        double step = 8;
        double x = 20 * GameProperties.BLOCK_WIDTH + step;
        double y = 6 * GameProperties.BLOCK_HEIGHT + 17;
        double width = GameProperties.BULLET_WIDTH;
        double height = GameProperties.BULLET_HEIGHT;

        double newX;
        switch (route) {
            case DOWN:
                newX = x + 2 * (width + step);
                break;
            case LEFT:
                newX = x + width + step;
                break;
            case RIGHT:
                newX = x + 3 * (width + step);
                break;
            default:
                newX = x;
        }

        imageView.setViewport(new Rectangle2D(newX, y, width, height));
    }
}
