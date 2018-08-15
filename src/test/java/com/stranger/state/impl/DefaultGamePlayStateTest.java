package com.stranger.state.impl;

import com.stranger.player.impl.GamePlayer;
import com.stranger.state.GameState;
import com.stranger.util.ConsoleManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConsoleManager.class, Files.class})
public class DefaultGamePlayStateTest {

    @InjectMocks
    private DefaultGamePlayState gamePlayState;

    @Mock
    private ObjectOutputStream oos;

    private GamePlayer player;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        player = new GamePlayer("Will", "16");
    }

    @Test
    public void should_Save_The_Game() throws IOException {

        Path newPath = Paths.get("stage-save.ser");

        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(
                Files.write(newPath, "1|1".getBytes())
        ).thenReturn(newPath);

        boolean saved = gamePlayState.save(1, 1, player);

        assertTrue(saved);
    }

}