package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Pair")
public class Pair extends ParseObject {
    public Pair(){}

    public static final String KEY_PROGRAM = "program";
    public static final String KEY_MENTOR = "mentor";
    public static final String KEY_MENTEE = "mentee";
    public static final String KEY_STATUS= "status";


//    public Program getProgram(){ return (Program) get(KEY_PROGRAM); }
//    public void setProgram(Program program){
//        put(KEY_PROGRAM, program);
//    }

    public ParseUser getMentor(){ return getParseUser(KEY_MENTOR); }
    public void setMentor(ParseUser participant){ put(KEY_MENTOR, participant); }

    public ParseUser getMentee(){ return getParseUser(KEY_MENTEE); }
    public void setMentee(ParseUser participant){ put(KEY_MENTEE, participant); }

    public String getStatus(){ return getString(KEY_STATUS); }
    public void setStatus(String status){ put(KEY_STATUS, status); }
}
