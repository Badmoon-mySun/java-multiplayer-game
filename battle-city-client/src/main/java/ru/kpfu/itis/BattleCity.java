package ru.kpfu.itis;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.kpfu.itis.controllers.GameController;
import ru.kpfu.itis.controllers.RoomSettingController;
import ru.kpfu.itis.net.Connection;
import ru.kpfu.itis.utils.ServerConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class BattleCity extends Application {
    private static Stage stage;

    private final long[] frameTimes = new long[100];
    private boolean arrayFilled = false;
    private int frameTimeIndex = 0 ;
    private double lastFPS = 0;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        AnimationTimer timerFPS = new AnimationTimer() {

            @Override
            public void handle(long now) {
                stage.setTitle(String.format("Battle city  FPS: %.3f", getFPS(now)));
            }
        };

        timerFPS.start();

        showMainMenu();

        stage.setResizable(false);
        stage.show();
    }

    public static void showGameView(ServerConnection connection) {
        Map<KeyCode, Boolean> keys = new HashMap<>();

        GameController controller = new GameController(connection, keys);

        Scene scene = new Scene(controller.getPane(), 624, 624, Color.BLACK);

        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        stage.setScene(scene);
        stage.show();
    }

    public static void showMainMenu() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BattleCity.class.getResource("/menu.fxml"));

        try {
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load menu");
        }
    }

    public static void showConfigurationRoom(ServerConnection connection) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BattleCity.class.getResource("/roomSetting.fxml"));

        try {
            Scene scene = new Scene(loader.load());

            RoomSettingController controller = loader.getController();
            controller.setConnection(connection);

            stage.setScene(scene);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load menu");
        }
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

    @Override
    public void stop() {
        System.exit(0);
    }
}
