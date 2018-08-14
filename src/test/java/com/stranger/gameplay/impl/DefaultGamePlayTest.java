package com.stranger.gameplay.impl;

import com.stranger.events.impl.DefaultGamePlayExplorationEvents;
import com.stranger.player.Player;
import com.stranger.player.impl.GamePlayer;
import com.stranger.stage.Stage;
import com.stranger.stage.StageMap;
import com.stranger.state.impl.DefaultGamePlayState;
import com.stranger.util.ConsoleManager;
import com.stranger.util.LoadAsset;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stranger.util.ConsoleManager.GREEN;
import static com.stranger.util.ConsoleManager.RED;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest( ConsoleManager.class )
public class DefaultGamePlayTest {

    @InjectMocks
    private DefaultGamePlay defaultGamePlay;

    @Mock
    private DefaultGamePlayExplorationEvents explorationEvents;

    @Mock
    private DefaultGamePlayState gamePlayState;

    @Mock
    private LoadAsset loadAsset;

    @Mock
    private InputStream inputStream;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_Set_Up_Stages() {
        List<Stage> stages = new ArrayList<>();
        List<StageMap> stageMaps = new ArrayList<>();
        Stage stage = new Stage();

        StageMap stageMap = new StageMap();
        stageMap.setMapName("school-1");
        stageMap.setStepsToFinish(1);

        stage.setMaps(stageMaps);
        stageMaps.add(stageMap);
        stages.add(stage);

        when(loadAsset.loadStageAssets("stranger-stuff")).thenReturn(stages);
        List<Stage> result = defaultGamePlay.setUpStages();

        assertNotNull(result);
        assertEquals(1, stages.size());
    }

    @Test
    public void should_Get_Background_Music_Asset() {
        Optional<InputStream> inputStreamOptional = Optional.of(this.inputStream);
        defaultGamePlay.setFolderGame("stranger-stuff");

        when(loadAsset.loadAsset("stranger-stuff", "/music-setup.txt"))
                .thenReturn(inputStreamOptional);

        Optional<InputStream> result = defaultGamePlay.getBackgroundMusicAsset();

        assertTrue(result.isPresent());
    }

    @Test
    public void should_Get_Title_Screen_Asset() {
        Optional<InputStream> inputStreamOptional = Optional.of(this.inputStream);
        defaultGamePlay.setFolderGame("stranger-stuff");

        when(loadAsset.loadAsset("stranger-stuff", "/title.txt"))
                .thenReturn(inputStreamOptional);

        Optional<InputStream> result = defaultGamePlay.getTitleScreen();

        assertTrue(result.isPresent());
    }

    @Test
    public void should_Create_A_Character_With_Name_Mike() {
        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.when(ConsoleManager.read()).thenReturn("Mike");

        Player player = defaultGamePlay.createCharacter();

        PowerMockito.verifyStatic(VerificationModeFactory.times(3));

        assertNotNull(player);
        assertEquals("Mike", player.getName());
    }

    @Test
    public void should_Create_A_Character_With_Age_21() {
        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.when(ConsoleManager.read()).thenReturn("21");

        Player player = defaultGamePlay.createCharacter();

        PowerMockito.verifyStatic(VerificationModeFactory.times(3));

        assertNotNull(player);
        assertEquals("21", player.getAge());
    }

    @Test
    public void should_Create_A_Character_With_Weapon_Sword() {
        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.when(ConsoleManager.read()).thenReturn("Sword");

        Player player = defaultGamePlay.createCharacter();

        PowerMockito.verifyStatic(VerificationModeFactory.times(3));

        assertNotNull(player);
        assertEquals("Sword", player.getWeapon());
    }

    @Test
    public void should_Display_Game_Intro() {
        Optional<InputStream> inputStreamOptional = Optional.of(this.inputStream);
        defaultGamePlay.setFolderGame("stranger-stuff");

        when(loadAsset.loadAsset("stranger-stuff", "/intro.txt"))
                .thenReturn(inputStreamOptional);

        GamePlayer gamePlayer = new GamePlayer("Mike", "18");
        gamePlayer.setWeapon("Sword");

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.printScrollableText(RED, inputStreamOptional.get(), 2000,
                gamePlayer.getName(), gamePlayer.getAge(), gamePlayer.getWeapon());

        defaultGamePlay.gameIntro(gamePlayer);

        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
    }

    @Test
    public void should_Explore_With_Search_Event_And_Keep_Exploring() {

        Stage stage = new Stage();
        StageMap stageMap = new StageMap();
        GamePlayer gamePlayer = new GamePlayer();

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(GREEN, "What you want to do?:\n1. Search\n2. Move.\n3. Save\n");

        PowerMockito.when(ConsoleManager.read()).thenReturn("1");

        Mockito.doNothing().when(explorationEvents).search(stageMap, gamePlayer);

        boolean result = defaultGamePlay.explore(stage, stageMap, gamePlayer, 1, 1);
        Mockito.verify(explorationEvents, times(1)).search(stageMap, gamePlayer);
        PowerMockito.verifyStatic(VerificationModeFactory.times(1));

        assertTrue(result);

    }

}