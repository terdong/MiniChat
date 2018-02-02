package com.donghee.server;

import com.donghee.protocol.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServerReceiver implements Runnable {

    private Broadcast server;
    private Socket socket;

    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;

    private String nickName = null;


    public MiniChatServerReceiver(Broadcast server, Socket socket) {
        this.server = server;
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
        boolean isConnected = true;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            server.broadcast(String.format("새로운 손님이 한분 입장하셨습니다.(총 %d명 접속 중)", server.getUserCount()));

            for (; isConnected && (obj = objectInputStream.readObject()) != null; ) {
                Protocol p = (Protocol) obj;
                message = p.message;
                if (message.equals("exit")) {
                    isConnected = false;
                    nickName = p.nickName;
                    socket.close();
                    break;
                } else if(!message.isEmpty()){
                    nickName = p.nickName;
                    message = String.format("[%s]: %s", nickName, message);
                    System.out.println(message);
                    server.broadcast(message);
                }
            }
            close();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            println("disconnect");
            message = String.format("[%s]님이 나가셨습니다.(총 %d명 접속 중)", nickName, server.getUserCount());
            server.broadcast(message);
            System.out.println(message);
            server.notiLeftUser(this);
        }
    }

    private void close() throws IOException{
        printWriter.close();
        System.out.println("PrintWriter close 완료");
        objectInputStream.close();
        System.out.println("ObjectInputStream close 완료");
    }
}
