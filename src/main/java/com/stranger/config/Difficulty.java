package com.stranger.config;

public enum Difficulty {

    EASY("School Boy"),
    NORMAL("Dig Dug(er)"),
    HARD("Ghostbuster");


    private String name;

    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
