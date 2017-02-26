package com.donghee.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServer implements Broadcast, SocketAdder, ICommander{

    private static final int Max_Client_Number = 20;

    private ServerSocket serverSocket;

    private MiniChatServerWaiter waiter;
    private MiniChatServerCommander commander;
    private HashSet<MiniChatServerReceiver> receivers;

    public MiniChatServer(){
        waiter = new MiniChatServerWaiter(this, this);
        commander = new MiniChatServerCommander(this, this);
        receivers = new HashSet<>();
    }

    public void start(){
        createServerSocket();
        new Thread(waiter).start();
        new Thread(commander).start();
    }

    @Override
    public void addSocket(Socket socket){
        MiniChatServerReceiver receiver = new MiniChatServerReceiver(this, this, socket);
        receivers.add(receiver);
        new Thread(receiver).start();
    }

    @Override
    public void displayCurrentUserCount(){
        System.out.println(String.format("현재 접속한 유저 수: %d명", receivers.size()));
    }

    @Override
    public void exit() {
        waiter.close();
        broadcast("서버가 종료됩니다. 감사합니다.");
        for( MiniChatServerReceiver receiver : receivers) {
            receiver.disconnect();
            receiver.close();
        }
        receivers.clear();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    private void createServerSocket(){
        try {
            serverSocket = new ServerSocket(5000);
            waiter.setServerSocket(serverSocket);
            System.out.println("서버 시작!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
