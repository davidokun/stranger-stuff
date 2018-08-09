package com.stranger.gameplay.impl;

import com.stranger.events.ExplorationEvents;
import com.stranger.events.FightEvents;
import com.stranger.gameplay.GameStoryLine;
import com.stranger.items.ItemGenerator;
import com.stranger.player.Player;
import com.stranger.player.impl.GamePlayer;
import com.stranger.stage.Stage;
import com.stranger.stage.StageMap;
import com.stranger.state.GameState;
import com.stranger.util.ConsoleManager;
import com.stranger.util.LoadAsset;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static com.stranger.util.ConsoleManager.GREEN;
import static com.stranger.util.ConsoleManager.RED;
import static com.stranger.util.ConsoleManager.YELLOW;

public class DefaultGamePlay implements GameStoryLine {

    private LoadAsset loadAsset;
    private ItemGenerator itemGenerator;
    private FightEvents fightEvents;
    private ExplorationEvents explorationEvents;
    private GameState gameState;
    public static String folderGame;

    @Override
    public List<Stage> setUpStages() {
        return loadAsset.loadStageAssets(folderGame);
    }


    @Override
    public Optional<InputStream> getBackgroundMusicAsset() {
        return loadAsset.loadAsset(folderGame,"/music-setup.txt");
    }

    @Override
    public Optional<InputStream> getTitleScreen() {
        return loadAsset.loadAsset(folderGame,"/title.txt");
    }

    @Override
    public Player createCharacter() {

        GamePlayer gamePlayer = new GamePlayer();

        ConsoleManager.print(YELLOW, "LET'S CREATE YOUR CHARACTER!!!!\n");

        ConsoleManager.print(GREEN, "Set player name :");
        gamePlayer.setName(ConsoleManager.read());

        ConsoleManager.print(GREEN, "Set player age :");
        gamePlayer.setAge(ConsoleManager.read());

        ConsoleManager.print(GREEN, "Set player weapon :");
        gamePlayer.setWeapon(ConsoleManager.read());

        return gamePlayer;
    }

    @Override
    public void gameIntro(Player player) {
        Optional<InputStream> path = loadAsset.loadAsset(folderGame, "/intro.txt");
        path.ifPresent(path1 -> {
            ConsoleManager.printScrollableText(RED, path1, 2000, player.getName(), player.getAge(), player.getWeapon());
        });
    }

    @Override
    public boolean explore(Stage stage, StageMap stageMap, Player player, int stageIndex, int mapIndex) {

        boolean keepExploring = true;

        ConsoleManager.print(GREEN, "What you want to do?:\n1. Search\n2. Move.\n3. Save\n");
        String selection = ConsoleManager.read();

        switch (selection) {
            case "1":
                this.explorationEvents.search(stageMap, player);
                break;
            case "2":
                keepExploring = this.explorationEvents.move(stageMap, player);
                break;
            case "3":
                gameState.save(stageIndex, mapIndex, player);
                break;
            default:
                ConsoleManager.print(RED, "Please select option (1, 2 or 3)");
        }

        return keepExploring;

    }

    @Override
    public GameState loadGame() {
        return gameState.load();
    }

    @Override
    public int[] recreateGameState() {

        int[] currentState = new int[2];

        String[] stageMapIndex = gameState.getStageSate().toString().split("\\|");

        currentState[0] = Integer.parseInt(stageMapIndex[0]);
        currentState[1] = Integer.parseInt(stageMapIndex[1]);

        return currentState;

    }

    @Override
    public void setFolderGame(String folderGame) {
        this.folderGame = folderGame;
    }

    @Override
    public void setExplorationEvents(ExplorationEvents explorationEvents) {
        this.explorationEvents = explorationEvents;
    }

    @Override
    public void setItemGenerator(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
    }

    @Override
    public void setFightEvents(FightEvents fightEvents) {
        this.fightEvents = fightEvents;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void setLoadAsset(LoadAsset loadAsset) {
        this.loadAsset = loadAsset;
    }
}
