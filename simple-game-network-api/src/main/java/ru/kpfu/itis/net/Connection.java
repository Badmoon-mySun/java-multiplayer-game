package ru.kpfu.itis.net;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.net.Socket;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class Connection {
    private final ConnectionListener eventListener;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final Socket socket;
    private final Thread thread;
    private Message message;

    public Connection(ConnectionListener eventListener, String ipAddr, int port) throws IOException {
        this(eventListener, new Socket(ipAddr, port));
    }

    public Connection(ConnectionListener eventListener, Socket socket) throws IOException {
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        this.eventListener = eventListener;
        this.socket = socket;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(Connection.this);
                    while (!thread.isInterrupted()) {
                        message = new Message(in.readByte());

                        while (in.available() > 0) {
                            byteArrayOutputStream.write(in.read());
                        }

                        message.setData(byteArrayOutputStream.toByteArray());

                        byteArrayOutputStream.reset();

                        eventListener.onReceiveMessage(Connection.this, message);
                    }
                } catch (IOException e) {
                    eventListener.onException(Connection.this, e);
                } finally {
                    eventListener.onDisconnect(Connection.this);
                }
            }
        });

        thread.start();
    }

    public synchronized void sendMessage(Message message) {
        try {
            out.write(ArrayUtils.addFirst(message.getData(), message.getType()));
            out.flush();
        } catch (IOException e) {
            eventListener.onException(Connection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(Connection.this, e);
        }
    }

    public Thread getThread() {
        return thread;
    }

    public Socket getSocket() {
        return socket;
    }
}
