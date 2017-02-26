package com.donghee.server;

import java.util.Scanner;

/**
 * Created by Administrator on 2017-02-26.
 */

public class MiniChatServerCommander implements Runnable {

    private Scanner scanner;
    private Broadcast broadcast;
    private ICommander commander;

    private boolean isAlive;

    public MiniChatServerCommander(Broadcast broadcast, ICommander commander){
        this.broadcast = broadcast;
        this.commander = commander;

        scanner = new Scanner(System.in);

        isAlive = true;
    }

    @Override
    public void run() {

        for(; isAlive;) {
            String command = scanner.nextLine();

            if(command.equals("/allUser")){
                commander.displayCurrentUserCount();
            }else if(command.equals("/exit")){
                System.out.println("서버를 종료하겠습니다.");
                commander.exit();
                isAlive = false;
            } else{
                broadcast.broadcast(String.format("[[운영자]]: %s",command));
            }
        }

        scanner.close();
    }
}
