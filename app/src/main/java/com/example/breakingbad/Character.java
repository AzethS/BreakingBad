package com.example.breakingbad;

import java.util.ArrayList;

//Domeinklasse
public class Character {
    //attributen
    private String name;
    private String imgUrl;
    private String nick;
    private String status;
    private String birth;
    private ArrayList<String> occupation;
    private ArrayList<Integer> appearance;

    public Character(String name, String imgUrl, String nick, String status, String birth, ArrayList<String> occupation, ArrayList<Integer> appearance) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.nick = nick;
        this.status = status;
        this.birth = birth;
        this.occupation = occupation;
        this.appearance = appearance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public ArrayList<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(ArrayList<String> occupation) {
        this.occupation = occupation;
    }

    public ArrayList<Integer> getAppearance() {
        return appearance;
    }

    public void setAppearance(ArrayList<Integer> appearance) {
        this.appearance = appearance;
    }
}
