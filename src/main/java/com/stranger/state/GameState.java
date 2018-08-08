package com.stranger.state;

import com.stranger.player.Player;

/**
 * Base contract to handle GameState
 */
public interface GameState {

    /**
     * Save the game state to a underlining persistent layer.
     *
     * @param stageIndex index of current stage.
     * @param mapIndex index of current map
     * @param player current player
     */
    boolean save(int stageIndex, int mapIndex, Player player);

    /**
     * Load the game state from the underlining persistent layer
     * @return the GameState for current player
     */
    GameState load();

    /**
     * Get the current player.
     * @return the player
     */
    Player getPlayer();

    /**
     * Get the stage state base on indexes.
     *
     * @return the stage stage.
     */
    StringBuilder getStageSate();
}
