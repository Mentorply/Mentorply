package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Message")
public class Message extends ParseObject implements Comparable<Message>{
    public static final String BODY_KEY = "body";
    public static final String KEY_FROM_USER = "fromUser";
    public static final String KEY_TO_USER = "toUser";
    public static final String KEY_CHAT_ROOM = "chatRoom";
    //private Date sentTime;

    public ParseUser getFromUser(){ return getParseUser(KEY_FROM_USER); }
    public void setFromUser(ParseUser fromUser){ put(KEY_FROM_USER, fromUser); }

    public ParseUser getToUser(){ return getParseUser(KEY_TO_USER); }
    public void setToUser(ParseUser toUser){ put(KEY_TO_USER, toUser); }


    public String getBody() { return getString(BODY_KEY); }

    public void setBody(String body) { put(BODY_KEY, body); }


    public Date getSentTime() {
        return getDate("createdAt");
    }

    public void setSentTime(Date sentTime) {
        put("createdAt", sentTime);
    }

    @Override
    public int compareTo(Message message) {
        if (getSentTime() == null || message.getSentTime() == null)
            return 0;
        return getSentTime().compareTo(message.getSentTime());
    }

}
