package com.teamgehem.minichat.launcher;

import com.teamgehem.minichat.server.MiniChatServer;

/**
 * Created by Donghee Kim on 2017-02-19.
 */

public class MiniChatServerLauncher {
    public static void main(String[] args) {
        MiniChatServer server = new MiniChatServer();
        server.start(5000);
    }
}