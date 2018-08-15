package com.stranger.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConsoleManager.class, Scanner.class, Thread.class})
public class ConsoleManagerTest {

    @InjectMocks
    private ConsoleManager consoleManager;

    @Mock
    private BufferedReader bufferedReader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_Print_Simple_Scrollable_Text() {

        InputStream is = new ByteArrayInputStream( "Hello World".getBytes() );
        ConsoleManager.printScrollableText(ConsoleManager.WHITE, is, 100);

    }

    @Test
    public void should_Print_Scrollable_Text_With_Params() {

        InputStream is = new ByteArrayInputStream( "Hello World %s".getBytes() );
        ConsoleManager.printScrollableText(ConsoleManager.WHITE, is, 100, "David");

    }

}