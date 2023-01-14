package com.teamgehem.minichat.protocol;

import java.io.Serializable;

/**
 * Created by Donghee Kim on 2017-02-25.
 */

public class Protocol implements Serializable{
    public static final long serialVersionUID  = 1;

    public Protocol(String nickName){
        this.nickName = nickName;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String nickName;
    public String message;

}