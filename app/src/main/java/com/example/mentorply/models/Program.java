package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@ParseClassName("Program")
public class Program extends ParseObject{
    public Program(){}
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROGRAM_PICTURE= "programPicture";
    public static final String KEY_MEMBERSHIPS = "memberships";
    public static final String KEY_PAIRS = "pairs";


    public String getName(){ return getString(KEY_NAME); }
    public void setName(String name){
        put(KEY_NAME, name);
    }

    public String getDescription(){ return getString(KEY_DESCRIPTION); }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){ return getParseFile(KEY_PROGRAM_PICTURE); }
    public void setImage(ParseFile parseFile){
        put(KEY_PROGRAM_PICTURE, parseFile);
    }

    public ArrayList getMemberships(){ return (ArrayList <Membership>) get(KEY_MEMBERSHIPS); }
    public void addMembership(Membership membership){ addUnique(KEY_MEMBERSHIPS, membership); }

    public ArrayList getPairs(){ return (ArrayList <Pair>) get(KEY_PAIRS); }
    public void addPair(Pair pair){ addUnique(KEY_PAIRS, pair); }

}