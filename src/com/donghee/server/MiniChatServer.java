package com.donghee.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServer implements Broadcast, SocketAdder{

    private static final int Max_Client_Number = 20;

    private ServerSocket serverSocket;

    private MiniChatServerWaiter waiter;
    private HashSet<MiniChatServerReceiver> receivers;

    public MiniChatServer(){
        waiter = new MiniChatServerWaiter(this);
        receivers = new HashSet<>();
    }

    public void start(int port){
        createServerSocket(port);
        new Thread(waiter).start();
    }

    public void start(){
        start(5000);
    }

    @Override
    public void addSocket(Socket socket){
        MiniChatServerReceiver receiver = new MiniChatServerReceiver(this, socket);
        receivers.add(receiver);
        new Thread(receiver).start();
        displayCurrentUserCount();
    }

    public void displayCurrentUserCount(){
        System.out.println(String.format("현재 접속한 유저 수: %d명", receivers.size()));
    }

    @Override
    public void broadcast(String message) {
        for( MiniChatServerReceiver receiver : receivers){
            if(receiver != null){
                receiver.println(message);
            }
        }
    }

    @Override
    public void notiLeftUser(MiniChatServerReceiver receiver) {
        receivers.remove(receiver);
        displayCurrentUserCount();
    }

    @Override
    public int getUserCount() {
        return receivers.size();
    }

    private void createServerSocket(int port){
        try {
            serverSocket = new ServerSocket(port);
            waiter.setServerSocket(serverSocket);
            System.out.printf("서버 시작!(포트번호: %d)\n", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
