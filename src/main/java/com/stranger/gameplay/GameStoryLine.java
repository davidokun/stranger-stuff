package com.stranger.gameplay;

import com.stranger.events.ExplorationEvents;
import com.stranger.events.FightEvents;
import com.stranger.items.ItemGenerator;
import com.stranger.player.Player;
import com.stranger.stage.Stage;
import com.stranger.stage.StageMap;
import com.stranger.state.GameState;
import com.stranger.util.LoadAsset;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface GameStoryLine {

    /**
     * Set the folder name to load the specific assets for the game.
     * @param folderGame the name of the asset's folder for the current game
     */
    void setFolderGame(String folderGame);

    /**
     * Load up the stages for the given game from the assets folders
     * @return the list of the Game's stages
     */
    List<Stage> setUpStages();

    /**
     * Set up an implementation of <code>{@link ExplorationEvents}</code> to handle the
     * game exploration options.
     * @param explorationEvents the implementation for the exploration options for the game.
     */
    void setExplorationEvents(ExplorationEvents explorationEvents);

    void setItemGenerator(ItemGenerator itemGenerator);

    void setFightEvents(FightEvents fightEvents);

    void setGameState(GameState gameState);

    /**
     * Set a background music if any will be available. This just load an asset with a
     * link to play music. Nothing fancy here :)
     */
    Optional<InputStream> getBackgroundMusicAsset();

    /**
     * Start game to load initial intro screen
     * @return an InputStream to print intro screen
     */
    Optional<InputStream> getTitleScreen();

    /**
     * Define character creation. Implementer will define different aspects of the character
     * @return a Player created
     */
    Player createCharacter();

    /**
     * It will print initial intro screen defined for the game
     * @param player a Player to put in the intro screen
     */
    void gameIntro(Player player);

    /**
     * Manage the stage exploration. Implementer must define the specific exloration options
     * for its specific stage
     * @param stageMap the stage to explore
     * @param player the current player
     * @return true if stage is on, false to finish stage
     */
    boolean explore(Stage stage, StageMap stageMap, Player player, int stageIndex, int mapIndex);

    /**
     * Load the state of the game from the persistence implementation
     * @return the <code>{@link GameState}</code> to resume.
     */
    GameState loadGame();

    /**
     * Re-creates the game state to resume the game.
     * @return an array with the indexes for the current Stage and Map.
     */
    int[] recreateGameState();

    void setLoadAsset(LoadAsset loadAsset);
}
