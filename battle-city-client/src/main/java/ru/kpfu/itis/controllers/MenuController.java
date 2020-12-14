package ru.kpfu.itis.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.kpfu.itis.BattleCity;
import ru.kpfu.itis.utils.ServerConnection;

import java.io.IOException;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class MenuController {
    private ServerConnection connection;

    @FXML
    public TextField serverIpField;

    @FXML
    public TextField connectToRoomField;

    @FXML
    public Label errorLabel;

    public void connectToRoom() {
        if(tryToConnect(serverIpField.getText())) {
            connection.sendRoomConnection(connectToRoomField.getText());

            BattleCity.showGameView(connection);
        }
    }

    public void createRoom() {
        if (tryToConnect(serverIpField.getText())) {
            connection.sendRoomCreate();

            BattleCity.showConfigurationRoom(connection);
        }
    }

    private boolean tryToConnect(String address) {
        try {
            String[] splitAdr = address.split(":");

            connection = new ServerConnection(splitAdr[0], Integer.parseInt(splitAdr[1]));

            return true;
        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            errorLabel.setText("Wrong server ip address!");
        } catch (IOException e) {
            errorLabel.setText("Connection to server failed");
        }

        return false;
    }
}
