package com.example.mentorply.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

@ParseClassName("ChatRoom")
public class ChatRoom extends ParseObject {
    public static final String KEY_MESSAGES= "messages";
    public static final String KEY_PAIR = "pair";

    public ArrayList getMessages(){ return (ArrayList <Message>) get(KEY_MESSAGES); }
    public void addMessage(Message message){ addUnique(KEY_MESSAGES, message); }

    public Pair getPair(){ return (Pair) get(KEY_PAIR); }
    public void setPair(Pair pair){ put(KEY_PAIR, pair); }



}
