package ru.kpfu.itis.entities.views.tanks;

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

public abstract class TankView extends SpriteView {
    private RouteMove lastMove;

    protected int lastFrame;
    protected int columns;
    protected double width, height;
    protected int xIndex, yIndex;
    protected int xIndexSource;

    public TankView(Image image, int xIndex, int yIndex) {
        super(new ImageView(image));
        width = GameProperties.BLOCK_WIDTH;
        height = GameProperties.BLOCK_HEIGHT;

        xIndexSource = xIndex;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        lastMove = RouteMove.UP;
        lastFrame = 0;
        columns = 2;

        this.imageView.setViewport(new Rectangle2D(xIndex * width, yIndex * height, width, height));
    }

    public void animate(RouteMove move) {
        if (lastMove.equals(move)) {
            setNextFrame();
        } else if (!move.equals(RouteMove.STOP)) {
            setMove(move);
            setNextFrame();
        }
    }

    private void setMove(RouteMove move) {
        lastMove = move;

        switch (move) {
            case UP:
                xIndex= xIndexSource;
                return;
            case DOWN:
                xIndex = xIndexSource + 4;
               return;
            case RIGHT:
                xIndex = xIndexSource + 6;
                return;
            case LEFT:
                xIndex = xIndexSource + 2;
        }
    }

    protected abstract void setNextFrame();
}
