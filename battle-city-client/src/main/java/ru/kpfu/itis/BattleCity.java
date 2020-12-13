package ru.kpfu.itis;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.kpfu.itis.controllers.GameController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BattleCity extends Application {
    private static Map<KeyCode, Boolean> keys = new HashMap<>();
    private final long[] frameTimes = new long[100];
    private boolean arrayFilled = false;
    private int frameTimeIndex = 0 ;
    private double lastFPS = 0;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Platform.setImplicitExit(false);

        GameController controller = new GameController(keys);

        AnimationTimer timerFPS = new AnimationTimer() {

            @Override
            public void handle(long now) {
                stage.setTitle(String.format("Battle city  FPS: %.3f", getFPS(now)));
            }
        };

        timerFPS.start();

        Scene scene = new Scene(controller.getPane(), 612, 612, Color.BLACK);

        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        stage.setScene(scene);
        stage.setTitle("");
        stage.setResizable(false);
        stage.show();
    }

    private double getFPS(long now) {
        long oldFrameTime = frameTimes[frameTimeIndex] ;
        frameTimes[frameTimeIndex] = now ;
        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;

        if (frameTimeIndex == 0) {
            arrayFilled = true ;
        }

        if (arrayFilled) {
            long elapsedNanos = now - oldFrameTime ;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
            lastFPS = 1_000_000_000.0 / elapsedNanosPerFrame;
        }

        return lastFPS;
    }
}
