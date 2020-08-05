package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String BODY_KEY = "body";
    public static final String KEY_FROM_USER = "fromUser";
    public static final String KEY_TO_USER = "toUser";


    public ParseUser getFromUser(){ return getParseUser(KEY_FROM_USER); }
    public void setFromUser(ParseUser fromUser){ put(KEY_FROM_USER, fromUser); }

    public ParseUser getToUser(){ return getParseUser(KEY_TO_USER); }
    public void setToUser(ParseUser toUser){ put(KEY_TO_USER, toUser); }


    public String getBody() { return getString(BODY_KEY); }

    public void setBody(String body) { put(BODY_KEY, body); }
}
