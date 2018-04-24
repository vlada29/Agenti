package com.chatapp.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Message implements Serializable {
    private User from;
    private ArrayList<String> to;
    private String date;
    private String subject;
    private String content;

    public Message() {}

    public Message(User from, ArrayList<String> to, String date, String subject, String content) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.subject = subject;
        this.content = content;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
