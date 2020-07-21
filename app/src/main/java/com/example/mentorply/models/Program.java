package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;


@ParseClassName("Program")
public class Program extends ParseObject{
    public Program(){}
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROGRAM_PICTURE= "programPicture";
    public static final String KEY_MENTORS = "mentors";
    public static final String KEY_MENTEES = "mentees";
    public static final String KEY_DIRECTOR = "programDirector";


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

    public ParseRelation getMentors(){return getRelation(KEY_MENTORS);}
    public void addMentor(ParseRelation relation, ParseUser user){ relation.add(user); }

    public ParseRelation getMentees(){return getRelation(KEY_MENTEES);}
    public void addMentee(ParseRelation relation, ParseUser user){ relation.add(user); }

    public ParseUser getDirector(){
        return getParseUser(KEY_DIRECTOR);
    }
    public void setDirector(ParseUser user){
        put(KEY_DIRECTOR, user);
    }


}
//classes/Program