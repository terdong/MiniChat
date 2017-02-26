package com.donghee.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServerWaiter implements Runnable{

    private SocketAdder server;
    private ICommander commander;
    private ServerSocket serverSocket;

    private boolean isAlive;

    public MiniChatServerWaiter(SocketAdder server, ICommander commander){

        this.server = server;
        this.commander = commander;
        isAlive = true;
    }

    public void setServerSocket(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        for(; isAlive;){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("accept 완료");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(socket != null){
                server.addSocket(socket);
                commander.displayCurrentUserCount();
            }
        }
    }

    public void close(){
        isAlive = false;
    }
}
