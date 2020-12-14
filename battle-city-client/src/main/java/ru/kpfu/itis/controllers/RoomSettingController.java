package ru.kpfu.itis.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ru.kpfu.itis.BattleCity;
import ru.kpfu.itis.utils.ServerConnection;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class RoomSettingController {
    private ServerConnection connection;

    @FXML
    public Button applyButton;
    @FXML
    public ToggleGroup playerCount;
    @FXML
    public ToggleGroup stage;
    @FXML
    public Label status;
    @FXML
    public Label roomKey;

    public void applySettings() {
        RadioButton selectedRadioButton = (RadioButton) playerCount.getSelectedToggle();
        byte pl = Byte.parseByte(selectedRadioButton.getText());

        selectedRadioButton = (RadioButton) stage.getSelectedToggle();
        byte st = Byte.parseByte(selectedRadioButton.getText());

        applyButton.setDisable(true);

        connection.sendRoomSettings(pl, st);

        status.setText("Waiting players...");
        roomKey.setText("Room key: " + connection.getRoomKey());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (connection.isGameRun()) {
                    BattleCity.showGameView(connection);

                    this.stop();
                }
            }
        };

        timer.start();
    }

    public void setConnection(ServerConnection connection) {
        this.connection = connection;
    }
}
