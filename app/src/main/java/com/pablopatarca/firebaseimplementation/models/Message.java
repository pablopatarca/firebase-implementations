package com.pablopatarca.firebaseimplementation.models;

import java.util.Calendar;

/**
 * Created by Pablo on 30/7/16.
 */
public class Message {

    public String userName;
    public String message;
    public long date;

    public Message(){
    }

    public Message(String user, String message){
        userName = user;
        this.message = message;
        date = Calendar.getInstance().getTimeInMillis();
    }

}
