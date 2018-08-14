package com.stranger.events.impl;

import com.stranger.gameplay.impl.DefaultGamePlay;
import com.stranger.player.Player;
import com.stranger.player.impl.GamePlayer;
import com.stranger.stage.StageMap;
import com.stranger.util.ConsoleManager;
import com.stranger.util.LoadAsset;
import com.stranger.util.RandomEventGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stranger.util.ConsoleManager.CYAN;
import static com.stranger.util.ConsoleManager.GREEN;
import static com.stranger.util.ConsoleManager.RED;
import static org.junit.Assert.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DefaultGamePlay.class, ConsoleManager.class, LoadAsset.class})
public class DefaultGamePlayFightEventsTest {

    @InjectMocks
    private DefaultGamePlayFightEvents fightEvents;

    @Mock
    private LoadAsset loadAsset;

    @Mock
    private List<Player> enemies;

    @Mock
    private InputStream inputStream;

    private StageMap stageMap;
    private GamePlayer gamePlayer;
    private List<Player> testEnemies;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        stageMap = new StageMap();
        gamePlayer = new GamePlayer();

        testEnemies = new ArrayList<>();
        GamePlayer enemy = new GamePlayer();
        enemy.setName("School-Boy");
        enemy.setAge("10");
        enemy.setWeapon("Book");
        enemy.setExperience(10);
        enemy.setLife(10);
        testEnemies.add(enemy);

        enemy = new GamePlayer();
        enemy.setName("Lab-Man");
        enemy.setAge("45");
        enemy.setWeapon("Taser");
        enemy.setExperience(20);
        enemy.setLife(20);
        testEnemies.add(enemy);
    }

    @Test
    public void should_Set_Enemies_To_Load() {
        Mockito.when(loadAsset.loadEnemyAssets()).thenReturn(enemies);

        fightEvents.loadEnemies();

        Mockito.verify(loadAsset, times(1))
                .loadEnemyAssets();
    }

    @Test
    public void should_Load_And_Fight_An_Enemy_Until_Is_Defeated() {
        Optional<InputStream> inputStreamOptional = Optional.of(this.inputStream);

        Mockito.when(enemies.size()).thenReturn(2);
        Mockito.when(enemies.get(Mockito.anyInt())).thenReturn(testEnemies.get(0));
        Mockito.when(loadAsset.loadAsset(Mockito.anyString(), Mockito.anyString())).thenReturn(inputStreamOptional);

        PowerMockito.mockStatic(LoadAsset.class);
        PowerMockito.when(LoadAsset.recreateAsset(inputStreamOptional.get())).thenReturn(inputStreamOptional.get());

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.printScrollableText(RED, inputStreamOptional.get(), 100);
        ConsoleManager.print(CYAN, "Select an action:\n1. Attack\n2. Leave");

        PowerMockito.when(ConsoleManager.read()).thenReturn("1");

        fightEvents.fight(stageMap, gamePlayer);

        Mockito.verify(enemies, times(1)).size();
        Mockito.verify(loadAsset, times(1)).loadAsset(Mockito.anyString(), Mockito.anyString());

        PowerMockito.verifyStatic(times(2));
        ConsoleManager.printScrollableText(Matchers.anyString(), Matchers.anyObject(), Matchers.anyInt());

        PowerMockito.verifyStatic(times(3));
        ConsoleManager.print(CYAN, "Select an action:\n1. Attack\n2. Leave");
    }

    @Test
    public void should_Load_But_Leave_Fight() {
        Optional<InputStream> inputStreamOptional = Optional.of(this.inputStream);

        Mockito.when(enemies.size()).thenReturn(2);
        Mockito.when(enemies.get(Mockito.anyInt())).thenReturn(testEnemies.get(0));
        Mockito.when(loadAsset.loadAsset(Mockito.anyString(), Mockito.anyString())).thenReturn(inputStreamOptional);

        PowerMockito.mockStatic(LoadAsset.class);
        PowerMockito.when(LoadAsset.recreateAsset(inputStreamOptional.get())).thenReturn(inputStreamOptional.get());

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.printScrollableText(RED, inputStreamOptional.get(), 100);
        ConsoleManager.print(CYAN, "Select an action:\n1. Attack\n2. Leave");

        PowerMockito.when(ConsoleManager.read()).thenReturn("2");

        fightEvents.fight(stageMap, gamePlayer);

        Mockito.verify(enemies, times(1)).size();
        Mockito.verify(loadAsset, times(1)).loadAsset(Mockito.anyString(), Mockito.anyString());

        PowerMockito.verifyStatic(times(1));
        ConsoleManager.printScrollableText(Matchers.anyString(), Matchers.anyObject(), Matchers.anyInt());

        PowerMockito.verifyStatic(times(2));
        ConsoleManager.print(CYAN, "Select an action:\n1. Attack\n2. Leave");
    }
}