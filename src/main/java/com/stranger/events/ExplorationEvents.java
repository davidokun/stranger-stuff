package com.stranger.events;

import com.stranger.items.ItemGenerator;
import com.stranger.player.Player;
import com.stranger.stage.StageMap;

/**
 * This interface setup the contract to handle Exploration events
 * in the game.
 */
public interface ExplorationEvents {

    /**
     * Define the Search logic for the game. Implementor will specify which
     * options to use.
     * @param stageMap the current map where the player is exploring.
     * @param player the current player.
     */
    void search(StageMap stageMap, Player player);

    /**
     * Define the options where the player moves around the map.
     * @param stageMap the current map where the player is exploring.
     * @param player the current player.
     * @return true if map is not finished, false otherwise.
     */
    boolean move(StageMap stageMap, Player player);

    void setItemGenerator(ItemGenerator strangerStuffItemGenerator);

    void setFightEvents(FightEvents fightEvents);
}
