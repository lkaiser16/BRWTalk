package com.example.brwtalk;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class Message
{
    String date;
    String user;
    String message;
    long id;

    public Message(String user, String message, String date, long id)
    {
        this.date = date;
        this.user = user;
        this.message = message;
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public String getUser()
    {
        return user;
    }

    public String getMessage()
    {
        return message;
    }

    public long getId()
    {
        return id;
    }
}
