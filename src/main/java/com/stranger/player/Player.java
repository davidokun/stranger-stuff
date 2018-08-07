package com.stranger.player;

import com.stranger.items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for a game player
 */
public abstract class Player implements Serializable {

    public static final long serialVersionUID = 4582135975825L;

    protected String name;
    protected String age;
    protected String weapon;
    protected List<Item> items = new ArrayList<>();
    protected int life;
    protected int expereince;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getExperience() {
        return expereince;
    }

    public void setExperience(int experience) {
        this.expereince = experience;
    }

}
