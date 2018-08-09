package com.stranger.items.impl;

import com.stranger.items.Item;
import com.stranger.items.ItemGenerator;
import com.stranger.util.RandomEventGenerator;

import java.util.ArrayList;
import java.util.List;

public class DefaultGamePlayItemGenerator implements ItemGenerator {

    private List<Item> strangerStuffItems;


    public DefaultGamePlayItemGenerator() {
        strangerStuffItems = new ArrayList<>();
        strangerStuffItems.add(new Item("Potion", 50));
        strangerStuffItems.add(new Item("Phoenix Down", 100));
        strangerStuffItems.add(new Item("Poison", 30));
        strangerStuffItems.add(new Item("Curaga", 40));

    }

    @Override
    public Item getItem(int itemIndex) {
        return strangerStuffItems.get(itemIndex);
    }

}
