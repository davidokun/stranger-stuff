package com.stranger.items;

/**
 * Defines which/how generate items for the game.
 */
public interface ItemGenerator {

    /**
     * Gets an specific item.
     * @param itemIndex index to retrieve the item
     * @return the item requested
     */
    Item getItem(int itemIndex);

}
