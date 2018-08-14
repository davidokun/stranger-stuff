package com.stranger.items.impl;

import com.stranger.items.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultGamePlayItemGeneratorTest {


    private DefaultGamePlayItemGenerator defaultGamePlayItemGenerator;

    @Before
    public void setUp() {
        defaultGamePlayItemGenerator = new DefaultGamePlayItemGenerator();
    }

    @Test
    public void should_Get_Single_Item() {

        Item item = defaultGamePlayItemGenerator.getItem(2);

        assertNotNull(item);
        assertEquals("Poison", item.getName());
        assertEquals(30, item.getPower());
    }
}