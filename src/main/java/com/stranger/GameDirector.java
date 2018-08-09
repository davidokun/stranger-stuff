package com.stranger;

import com.stranger.events.ExplorationEvents;
import com.stranger.events.FightEvents;
import com.stranger.gameplay.GameStoryLine;
import com.stranger.items.ItemGenerator;
import com.stranger.player.Player;
import com.stranger.stage.Stage;
import com.stranger.stage.StageMap;
import com.stranger.state.GameState;
import com.stranger.util.ConsoleManager;
import com.stranger.util.LoadAsset;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static com.stranger.util.ConsoleManager.*;

/**
 * Class for directing the flow of the game.
 */
public class GameDirector {

    private static final String SEPARATOR = "=";

    public void gameFlow() {

        LoadAsset loadAsset = new LoadAsset();
        loadAsset.loadGameConfig(LoadAsset.gameConfig, "game-config.txt");
        loadAsset.loadGameConfig(LoadAsset.contextConfig, "game-context.txt");

        GameStoryLine game = null;

        try {
            game = setGameDependencies(loadAsset, LoadAsset.contextConfig[0].split("=")[1]);

            Optional<InputStream> backgroundMusicAsset = game.getBackgroundMusicAsset();
            backgroundMusicAsset.ifPresent(music -> ConsoleManager.printScrollableText(RED, music, 500));

            ConsoleManager.print(GREEN,"\n\nReady? (Press Y): ");
            ConsoleManager.read();

            Optional<InputStream> title = game.getTitleScreen();

            if (title.isPresent()) {
                ConsoleManager.printScrollableText(RED, title.get(), 100);

                boolean isGameOn = true;

                while (isGameOn) {

                    ConsoleManager.print(GREEN, "\n\nSelect option to star game: ");
                    String option = ConsoleManager.read();

                    switch (option) {
                        case "1":
                            Player player = game.createCharacter();
                            game.gameIntro(player);

                            gameStart(game, player, false);

                            finishGame();
                            isGameOn = false;
                            break;
                        case "2":
                            GameState currentGameState = game.loadGame();
                            gameStart(game, currentGameState.getPlayer(), true);
                            finishGame();
                            isGameOn = false;
                            break;
                        case "3":
                            ConsoleManager.print(BLUE, "Are you chicken out? ok, bye!!!");
                            isGameOn = false;
                            break;
                        default:
                            ConsoleManager.print(RED, "Please select an option (1,2,3)");
                            break;

                    }
                }
            }


            System.exit(0);

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            ConsoleManager.print(RED, "Oppps the game can't start. Do you provide the correct game name?");
        }



    }

    public void gameStart(GameStoryLine game, Player player, boolean isLoadGame) {

        List<Stage> stages = game.setUpStages();

        int currentStageIndex = 0;
        int currentMapIndex = 0;

        // Load the current index for the stage and map
        if (isLoadGame) {
            int[] currentState = game.recreateGameState();
            currentStageIndex = currentState[0];
            currentMapIndex = currentState[1];
        }

        for (int i = currentStageIndex; i < stages.size(); i++) {
            Stage stage = stages.get(i);
            boolean stageOn = true;

            while (stageOn) {

                for (int j = currentMapIndex; j < stage.getMaps().size(); j++) {

                    StageMap map = stage.getMaps().get(j);

                    boolean mapIsOn = true;

                    while (mapIsOn) {

                        ConsoleManager.print(GREEN, stage.getStageName() + " - " + map.getMapName());
                        ConsoleManager.printScrollableText(PURPLE, map.getGraphicArea(), 50);

                        mapIsOn = game.explore(stage, map, player, i, j);
                    }

                    ConsoleManager.print(RED, "You have pass this Level!!");
                }

                stageOn = false;
            }
        }

    }

    public void finishGame() {
        ConsoleManager.print(GREEN, "You have finish the Game!!! ");
        ConsoleManager.print(GREEN, "Tanks for Playing!!! ");
    }

    /**
     * Setup the dependency tree between all components.
     *
     * @return a <code>{@link GameStoryLine}</code> implementation for a configured game.
     */
    public GameStoryLine setGameDependencies(LoadAsset loadAsset, String gameFolderName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        GameStoryLine game;

        game = (GameStoryLine) Class.forName(LoadAsset.contextConfig[1].split(SEPARATOR)[1]).newInstance();
        ItemGenerator itemGenerator =
                (ItemGenerator) Class.forName(LoadAsset.contextConfig[2].split(SEPARATOR)[1]).newInstance();
        FightEvents fightEvents =
                (FightEvents) Class.forName(LoadAsset.contextConfig[3].split(SEPARATOR)[1]).newInstance();
        ExplorationEvents explorationEvents =
                (ExplorationEvents) Class.forName(LoadAsset.contextConfig[4].split(SEPARATOR)[1]).newInstance();
        GameState gameState =
                (GameState) Class.forName(LoadAsset.contextConfig[5].split(SEPARATOR)[1]).newInstance();

        fightEvents.setLoadAsset(loadAsset);
        fightEvents.loadEnemies();

        explorationEvents.setItemGenerator(itemGenerator);
        explorationEvents.setFightEvents(fightEvents);

        game.setItemGenerator(itemGenerator);
        game.setFightEvents(fightEvents);
        game.setExplorationEvents(explorationEvents);
        game.setGameState(gameState);
        game.setLoadAsset(loadAsset);
        game.setFolderGame(gameFolderName);

        return game;
    }
}
