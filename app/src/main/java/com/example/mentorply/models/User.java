package com.example.mentorply.models;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("_User")
public class User extends ParseUser {
    public User(){}
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROFILE_IMAGE = "profilePicture";
    public static final String KEY_AFFILIATIONS = "affiliations";
    public static final String KEY_TAGS = "tags";


    public String getDescription(){ return getString(KEY_DESCRIPTION); }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){return getParseFile(KEY_PROFILE_IMAGE); }
    public void setImage(ParseFile parseFile){
        put(KEY_PROFILE_IMAGE, parseFile);
    }

    public ArrayList getAffiliations(){ return (ArrayList <Affiliation>) get(KEY_AFFILIATIONS); }
    public void addAffiliation(Affiliation affiliation){ addUnique(KEY_AFFILIATIONS, affiliation); }

    public ArrayList getTags(){ return (ArrayList <Tag>) get(KEY_TAGS); }
    public void addTag(Tag tag){ addUnique(KEY_TAGS, tag); }



}
