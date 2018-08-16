package com.stranger.util;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class RandomEventGeneratorTest {


    @Test
    public void shouldReturnInteger() {

        int result = RandomEventGenerator.generateOption(1, 3);
        Assert.assertNotNull(result);

    }

    @Test
    public void shouldReturnIntegerLessThan4() {

        int result = RandomEventGenerator.generateOption(1, 3);
        assertTrue(result < 4);

    }
}