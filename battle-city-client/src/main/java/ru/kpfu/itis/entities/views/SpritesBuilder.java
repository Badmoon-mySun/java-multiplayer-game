package ru.kpfu.itis.entities.views;

import javafx.scene.image.Image;
import ru.kpfu.itis.entities.views.blocks.BlockView;
import ru.kpfu.itis.entities.views.blocks.StaticBlockView;
import ru.kpfu.itis.entities.views.bullets.BulletView;
import ru.kpfu.itis.entities.views.tanks.SimpleTankView;
import ru.kpfu.itis.entities.views.tanks.TankView;
import ru.kpfu.itis.others.EntityType;
import ru.kpfu.itis.others.RouteMove;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class SpritesBuilder {
    private static Image image;

    static {
        try (InputStream inputStream = SpritesBuilder.class.getResourceAsStream("/sprites.png")) {
            image = new Image(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static BlockView getBlockView(byte sign) {
        if (sign == EntityType.BRICK_BLOCK) {
            return new StaticBlockView(image, 16, 0);
        }else if (sign == EntityType.LASTING_BLOCK) {
            return new StaticBlockView(image, 16, 1);
        } else {
            throw new IllegalStateException("Block sprite not found");
        }
    }

    public static BulletView getBulletView(RouteMove route) {
        return new BulletView(image, route);
    }

    public static TankView getTankSprite(int num) {
        switch (num) {
            case EntityType.PLAYER_1:
                return new SimpleTankView(image, 0, 0);
            case EntityType.PLAYER_2:
                return new SimpleTankView(image, 8, 8);
            default:
                throw new IllegalStateException("Tank sprite not found");
        }
    }
}
