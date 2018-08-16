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

    @Test
    public void should_Load_Stages_Assets() {

        LoadAsset.gameConfig[0] = "School,Woods,Upside-Down";

        String folderName = "stranger-stuff";
        List<Stage> stages = loadAsset.loadStageAssets(folderName);

        assertNotNull(stages);
        assertEquals(3, stages.size());
        assertEquals("School", stages.get(0).getStageName());
        assertEquals("Woods", stages.get(1).getStageName());
        assertEquals("Upside-Down", stages.get(2).getStageName());
    }

    @Test
    public void should_Load_Map_Stages_Assets() {

        LoadAsset.gameConfig[0] = "School,Woods,Upside-Down";

        String folderName = "stranger-stuff";
        List<Stage> stages = loadAsset.loadStageAssets(folderName);

        assertNotNull(stages);
        assertEquals(3, stages.size());
        assertEquals("school-1", stages.get(0).getMaps().get(0).getMapName());
        assertEquals("woods-1", stages.get(1).getMaps().get(0).getMapName());
        assertEquals("upside-down-1", stages.get(2).getMaps().get(0).getMapName());

    }

    @Test
    public void should_Load_Enemy_Assets() {

        LoadAsset.gameConfig[1] = "School-Boy,10,Book,10,10|Lab-Man,45,Taser,20,20";

        List<Player> enemies = loadAsset.loadEnemyAssets();

        assertNotNull(enemies);
        assertEquals(2, enemies.size());
        assertEquals("School-Boy", enemies.get(0).getName());
        assertEquals("10", enemies.get(0).getAge());
        assertEquals("Book", enemies.get(0).getWeapon());
        assertEquals(10, enemies.get(0).getExperience());
        assertEquals(10, enemies.get(0).getLife());

        assertEquals("Lab-Man", enemies.get(1).getName());
        assertEquals("45", enemies.get(1).getAge());
        assertEquals("Taser", enemies.get(1).getWeapon());
        assertEquals(20, enemies.get(1).getExperience());
        assertEquals(20, enemies.get(1).getLife());
    }
}