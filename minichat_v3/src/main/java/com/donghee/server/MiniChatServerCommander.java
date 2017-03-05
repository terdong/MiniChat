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

        System.out.println("도움말을 보시려면 /help를 입력하세요.");

        for(; isAlive;) {
            String command = scanner.nextLine();

            if(command.equals("/help")){
                System.out.println("명령어 리스트");
                System.out.println("/allUser: 접속한 모든 유저 수");
                System.out.println("/exit: 서버종료");
            }
            else if(command.equals("/allUser")){

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
