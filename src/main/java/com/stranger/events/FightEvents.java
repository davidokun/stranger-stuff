package com.stranger.events;

import com.stranger.player.Player;
import com.stranger.stage.StageMap;
import com.stranger.util.LoadAsset;

/**
 * Interface to define events for a Fight.
 */
public interface FightEvents {

    /**
     * Load all enemies configured from the asset folder.
     */
    void loadEnemies();

    /**
     * Define the fight behaviour to implement in the game.
     *
     * @param stageMap current displayed map.
     * @param player current player.
     */
    void fight(StageMap stageMap, Player player);

    /**
     * Define behaviour about managing the turns of a fight.
     *
     * @param player current player
     * @param enemy current fighting enemy.
     * @param isPlayer true if is the player turn, false if is th enemy's.
     */
    void characterTurn(Player player, Player enemy, boolean isPlayer);

    /**
     * Sets the LoadAsset instance.
     * @param loadAsset the <code>LoadAsset</code> instance.
     */
    void setLoadAsset(LoadAsset loadAsset);
}
