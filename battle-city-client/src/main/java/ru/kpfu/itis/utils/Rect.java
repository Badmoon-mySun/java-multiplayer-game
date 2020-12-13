package ru.kpfu.itis.utils;

import javafx.geometry.Rectangle2D;
import ru.kpfu.itis.entities.models.Model;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Rect {

    public static boolean rectCollision(Model first, Model second) {
        if (second.getHeight() != 0 && second.getWidth() != 0
                && first.getWidth() != 0 && first.getHeight() != 0) {

            return new Rectangle2D(first.getX(), first.getY(), first.getWidth(), first.getHeight())
                    .intersects(second.getX(), second.getY(), second.getWidth(), second.getHeight());
        }

        return false;
    }
}
