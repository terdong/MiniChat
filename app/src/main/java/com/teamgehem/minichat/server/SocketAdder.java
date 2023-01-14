package com.teamgehem.minichat.server;

import java.net.Socket;

/**
 * Created by Donghee Kim on 2017-02-19.
 */

public interface SocketAdder {
    void addSocket(Socket socket);
}