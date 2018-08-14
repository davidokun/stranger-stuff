package com.stranger.events.impl;

import com.stranger.items.Item;
import com.stranger.items.impl.DefaultGamePlayItemGenerator;
import com.stranger.player.impl.GamePlayer;
import com.stranger.stage.StageMap;
import com.stranger.util.ConsoleManager;
import com.stranger.util.RandomEventGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConsoleManager.class, RandomEventGenerator.class})
public class DefaultGamePlayExplorationEventsTest {

    @InjectMocks
    private DefaultGamePlayExplorationEvents explorationEvents;

    @Mock
    private DefaultGamePlayItemGenerator itemGenerator;

    @Mock
    private DefaultGamePlayFightEvents fightEvents;

    private List<Item> items;
    private GamePlayer player;
    private StageMap stageMap;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        player = new GamePlayer();
        stageMap = new StageMap();

        items = new ArrayList<>();
        items.add(new Item("Potion", 50));
        items.add(new Item("Phoenix Down", 100));
        items.add(new Item("Poison", 30));
        items.add(new Item("Curaga", 40));

    }

    @Test
    public void should_Find_An_Item_On_Search() {

        Mockito.when(itemGenerator.getItem(3)).thenReturn(items.get(3));

        PowerMockito.mockStatic(RandomEventGenerator.class);
        PowerMockito.when(RandomEventGenerator.generateOption(1, 3)).thenReturn(1);
        PowerMockito.when(RandomEventGenerator.generateOption(0, 3)).thenReturn(3);

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(anyString(), anyString());

        explorationEvents.search(stageMap, player);

        assertEquals(1, player.getItems().size());
        assertEquals("Curaga", player.getItems().get(0).getName());

    }

    @Test
    public void should_Not_Find_An_Item_On_Search() {

        PowerMockito.mockStatic(RandomEventGenerator.class);
        PowerMockito.when(RandomEventGenerator.generateOption(1, 3)).thenReturn(2);

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(anyString(), anyString());

        explorationEvents.search(stageMap, player);

        assertEquals(0, player.getItems().size());

        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        ConsoleManager.print(anyString(), anyString());

    }

    @Test
    public void should_Find_An_Enemy_On_Search() {

        Mockito.doNothing().when(fightEvents).fight(stageMap, player);

        PowerMockito.mockStatic(RandomEventGenerator.class);
        PowerMockito.when(RandomEventGenerator.generateOption(1, 3)).thenReturn(3);

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(anyString(), anyString());

        explorationEvents.search(stageMap, player);

        assertEquals(0, player.getItems().size());

        Mockito.verify(fightEvents, Mockito.times(1)).fight(stageMap, player);

        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        ConsoleManager.print(anyString(), anyString());

    }

    @Test
    public void should_Advance_One_Step_And_Keep_Moving_In_Stage() {

        stageMap.setStepsToFinish(3);
        PowerMockito.mockStatic(RandomEventGenerator.class);
        PowerMockito.when(RandomEventGenerator.generateOption(0, 1)).thenReturn(0);

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(ConsoleManager.GREEN, "You're " + 2 + " steps away from exit");

        boolean result = explorationEvents.move(stageMap, player);

        assertTrue(result);
        assertEquals(2, stageMap.getStepsToFinish());

        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        ConsoleManager.print(ConsoleManager.GREEN, "You're " + 2 + " steps away from exit");
    }

    @Test
    public void should_Advance_One_Step_And_Stop_Moving_In_Stage() {

        stageMap.setStepsToFinish(1);
        PowerMockito.mockStatic(RandomEventGenerator.class);
        PowerMockito.when(RandomEventGenerator.generateOption(0, 1)).thenReturn(0);


        boolean result = explorationEvents.move(stageMap, player);

        assertFalse(result);
        assertEquals(0, stageMap.getStepsToFinish());

    }

    @Test
    public void should_Find_Enemy_While_Moving_In_Stage() {

        stageMap.setStepsToFinish(3);

        PowerMockito.doNothing().when(fightEvents).fight(stageMap, player);

        PowerMockito.mockStatic(RandomEventGenerator.class);
        PowerMockito.when(RandomEventGenerator.generateOption(0, 1)).thenReturn(1);

        PowerMockito.mockStatic(ConsoleManager.class);
        PowerMockito.doNothing().when(ConsoleManager.class);
        ConsoleManager.print(ConsoleManager.CYAN, "You Have Found An Enemy!! Fight!!");

        boolean result = explorationEvents.move(stageMap, player);

        assertTrue(result);
        assertEquals(3, stageMap.getStepsToFinish());

        Mockito.verify(fightEvents, Mockito.times(1)).fight(stageMap, player);
        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        ConsoleManager.print(ConsoleManager.CYAN, "You Have Found An Enemy!! Fight!!");
    }
}