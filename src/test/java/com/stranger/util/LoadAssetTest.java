package com.stranger.util;

import com.stranger.player.Player;
import com.stranger.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoadAssetTest {

    @InjectMocks
    private LoadAsset loadAsset;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_Load_Game_Config() {

        String[] gameConfig = new String[2];
        loadAsset.loadGameConfig(gameConfig, "game-config.txt");

        assertNotNull(gameConfig);
        assertEquals(2, gameConfig.length);
        assertEquals("School,Woods,Upside-Down", gameConfig[0]);
        assertEquals("School-Boy,10,Book,10,10|Lab-Man,45,Taser,20,20", gameConfig[1]);
    }

    @Test
    public void should_Load_Assest_From_Class_Path() {

        Optional<InputStream> asset = loadAsset.loadAsset("stranger-stuff", "/intro.txt");
        assertNotNull(asset.get());
    }

    @Test
    public void should_Convert_InputStream_To_ByteArrayOutputStream() {

        InputStream is = new ByteArrayInputStream( "Test text".getBytes() );
        ByteArrayOutputStream output = LoadAsset.convertToByteArray(is);

        assertNotNull(output);
        assertEquals("Test text", output.toString());
    }

    @Test
    public void should_Recreate_Asset() throws IOException {

        InputStream is = new ByteArrayInputStream( "Test text".getBytes() );
        InputStream newInputStream = LoadAsset.recreateAsset(is);

        assertNotNull(newInputStream);
        assertEquals(84, newInputStream.read());
    }

}