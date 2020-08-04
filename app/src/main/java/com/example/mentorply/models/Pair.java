package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Pair")
public class Pair extends ParseObject {
    public Pair(){}

    public static final String KEY_PROGRAM = "program";
    public static final String KEY_FROM_USER = "fromUser";
    public static final String KEY_TO_USER = "toUser";
    public static final String KEY_STATUS= "status";


//    public Program getProgram(){ return (Program) get(KEY_PROGRAM); }
//    public void setProgram(Program program){
//        put(KEY_PROGRAM, program);
//    }

    public ParseUser getFromUser(){ return getParseUser(KEY_FROM_USER); }
    public void setFromUser(ParseUser fromUser){ put(KEY_FROM_USER, fromUser); }

    public ParseUser getToUser(){ return getParseUser(KEY_TO_USER); }
    public void setToUser(ParseUser toUser){ put(KEY_TO_USER, toUser); }

    public String getStatus(){ return getString(KEY_STATUS); }
    public void setStatus(String status){ put(KEY_STATUS, status); }
}
