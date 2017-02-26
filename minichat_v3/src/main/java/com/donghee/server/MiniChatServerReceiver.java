package com.donghee.server;

import com.donghee.protocol.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServerReceiver implements Runnable {

    private Broadcast server;
    private ICommander commander;
    private Socket socket;

    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;

    private Protocol protocol;

    public MiniChatServerReceiver(Broadcast server, ICommander commander, Socket socket) {
        this.server = server;
        this.commander = commander;
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String message) {
        printWriter.println(message);
    }

    @Override
    public void run() {
        String message;
        Object obj;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            for (; (obj = objectInputStream.readObject()) != null; ) {
                protocol = (Protocol) obj;
                message = protocol.message;
                if (message.equals("exit")) {
                    disconnect();
                    break;
                } else {
                    message = String.format("[%s]: %s", protocol.nickName, protocol.message);
                    System.out.println(message);
                    server.broadcast(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        close();
    }

    public  void disconnect(){
        println("disconnect");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = String.format("[%s]님이 나가셨습니다.", protocol.nickName);
        server.broadcast(message);
        System.out.println(message);
        server.notiLeftUser(this);
        commander.displayCurrentUserCount();
    }

    public void close() {
        printWriter.close();
        System.out.println("PrintWriter close 완료");
        try {
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ObjectInputStream close 완료");
    }
}
