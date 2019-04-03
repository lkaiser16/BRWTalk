package com.example.brwtalk;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class Message
{
    Date date;
    FirebaseUser user;
    String message;
    long id;

    public Message(FirebaseUser user, String message, Date date, long id)
    {
        this.date = date;
        this.user = user;
        this.message = message;
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public FirebaseUser getUser()
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
