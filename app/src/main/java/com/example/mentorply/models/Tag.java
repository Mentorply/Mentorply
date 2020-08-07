package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("Tag")
public class Tag extends ParseObject {
    public Tag(){}
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY= "category";
    public static final String KEY_USERS= "users";


    public String getName(){ return getString(KEY_NAME); }
    public void setName(String name){
        put(KEY_NAME, name);
    }

    public String getCategory(){ return getString(KEY_CATEGORY); }
    public void setCategory(String category){
        put(KEY_CATEGORY, category);
    }

    public ArrayList getUsers(){ return (ArrayList <ParseUser>) get(KEY_USERS); }
    public void addUsers(ParseUser user){ addUnique(KEY_USERS, user); }

}
