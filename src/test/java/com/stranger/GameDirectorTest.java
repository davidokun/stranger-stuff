package com.stranger;

import com.stranger.events.impl.DefaultGamePlayExplorationEvents;
import com.stranger.gameplay.GameStoryLine;
import com.stranger.gameplay.impl.DefaultGamePlay;
import com.stranger.player.Player;
import com.stranger.player.impl.GamePlayer;
import com.stranger.stage.Stage;
import com.stranger.stage.StageMap;
import com.stranger.util.ConsoleManager;
import com.stranger.util.LoadAsset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.omg.CORBA.PolicyError;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.stranger.util.ConsoleManager.GREEN;
import static com.stranger.util.ConsoleManager.PURPLE;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConsoleManager.class})
public class GameDirectorTest {

    @InjectMocks
    private GameDirector gameDirector;

    @Mock
    private GameStoryLine gameStoryLine;

    @Mock
    private DefaultGamePlayExplorationEvents explorationEvents;

    private Stage stage;
    private StageMap stageMap;
    private Player player;
    private List<Stage> stages;
    private List<StageMap> stageMaps;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        stages = new ArrayList<>();
        stageMaps = new ArrayList<>();
        stage = new Stage();
        stage.setStageName("School");

        stageMap = new StageMap();
        stageMap.setMapName("school-1");
        stageMap.setStepsToFinish(1);
        stageMap.setGraphicArea(new ByteArrayInputStream( "Test text".getBytes()));

        stage.setMaps(stageMaps);
        stageMaps.add(stageMap);
        stages.add(stage);
    }

    @Test
    public void should_Set_Game_Class_Dependencies() throws IllegalAccessException, ClassNotFoundException, InstantiationException {

        String gameFolder = "stranger-stuff";
        LoadAsset loadAsset = new LoadAsset();
        loadAsset.loadGameConfig(LoadAsset.gameConfig, "game-config.txt");
        loadAsset.loadGameConfig(LoadAsset.contextConfig, "game-context.txt");

        GameStoryLine gameStoryLine = gameDirector.setGameDependencies(loadAsset, gameFolder);

        assertNotNull(gameStoryLine);

    }

    @Test
    public void should_Print_Finish_Game_Lines() {

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(GREEN, "You have finish the Game!!! ");
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(GREEN, "Tanks for Playing!!! ");

        gameDirector.finishGame();

        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        ConsoleManager.print(GREEN, "You have finish the Game!!! ");
        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        ConsoleManager.print(GREEN, "Tanks for Playing!!! ");
    }

    
}