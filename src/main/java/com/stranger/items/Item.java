package com.stranger.items;

import java.io.Serializable;

/**
 * Model class for an Item
 */
public class Item implements Serializable {

    public static final long serialVersionUID = 135748238452L;

    private String name;
    private int power;

    public Item(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
