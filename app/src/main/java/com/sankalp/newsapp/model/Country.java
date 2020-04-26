package com.sankalp.newsapp.model;

public class Country {

    private int flagIcon;
    private String name;
    private int id;

    public Country setFlagIcon(int flagIcon) {
        this.flagIcon = flagIcon;
        return this;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Country setId(int id) {
        this.id = id;
        return this;
    }

    public int getFlagIcon() {
        return flagIcon;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }



}
