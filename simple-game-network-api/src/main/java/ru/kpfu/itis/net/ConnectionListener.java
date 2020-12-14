package ru.kpfu.itis.net;


/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public interface ConnectionListener {
    void onConnectionReady(Connection connection);

    void onReceiveMessage(Connection connection, Message message);

    void onDisconnect(Connection connection);

    void onException(Connection connection, Exception exp);
}
