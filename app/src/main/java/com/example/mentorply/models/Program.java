package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Program")
public class Program extends ParseObject{
    public Program(){}
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROGRAM_PICTURE= "programPicture";
    //public static final String KEY_OBJECT_ID = "objectId";
    //public static final String KEY_PROGRAM_CODE = "objectId";

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

    //public String getObjectId(){ return getString(KEY_OBJECT_ID); }

    //public String getProgramCode(){ return getString(KEY_PROGRAM_CODE); }
}
//classes/Program