package com.teamgehem.minichat.launcher;

import com.teamgehem.minichat.client.MiniChatClient;

/**
 * Created by Donghee Kim on 2017-02-19.
 */

public class MiniChatClientLauncher {
    public static void main(String[] args) {
        MiniChatClient client = new MiniChatClient();
        client.start();
    }
}