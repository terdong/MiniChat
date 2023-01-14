package com.teamgehem.minichat.server;

/**
 * Created by Donghee Kim on 2017-02-19.
 */

public interface Broadcast {
    void broadcast(String message);
    void notiLeftUser(MiniChatServerReceiver receiver);
    int getUserCount();
}