package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Membership")
public class Membership extends ParseObject {
    public Membership(){}

    public static final String KEY_PARTICIPANT = "participant";
    public static final String KEY_PROGRAM = "program";
    public static final String KEY_ROLE= "role";

    public ParseUser getParticipant(){ return getParseUser(KEY_PARTICIPANT); }
    public void setParticipant(ParseUser participant){
        put(KEY_PARTICIPANT, participant);
    }

    public Program getProgram(){ return (Program) get(KEY_PROGRAM); }
    public void setProgram(Program program){
        put(KEY_PROGRAM, program);
    }

    public String getRole(){ return getString(KEY_ROLE); }
    public void setRole(String role){
        put(KEY_ROLE, role);
    }

}
