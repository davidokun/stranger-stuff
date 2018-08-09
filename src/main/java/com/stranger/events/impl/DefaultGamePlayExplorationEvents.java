package com.stranger.events.impl;

import com.stranger.events.ExplorationEvents;
import com.stranger.events.FightEvents;
import com.stranger.items.Item;
import com.stranger.items.ItemGenerator;
import com.stranger.player.Player;
import com.stranger.stage.StageMap;
import com.stranger.util.ConsoleManager;
import com.stranger.util.RandomEventGenerator;

public class DefaultGamePlayExplorationEvents implements ExplorationEvents {

    private static final String FOUND_ITEM = "You have found a ";
    private static final String FOUND_NOTHING = "You have found nothing :(!!";
    private static final String FOUND_ENEMY = "You Have Found An Enemy!! Fight!!";

    private ItemGenerator itemGenerator;
    private FightEvents fightEvents;

    @Override
    public void search(StageMap stageMap, Player player) {

        int option = RandomEventGenerator.generateOption(1, 3);

        switch (option) {
            case 1:
                Item item = itemGenerator.getItem(RandomEventGenerator.generateOption(0, 3));
                ConsoleManager.print(ConsoleManager.CYAN, FOUND_ITEM + item.getName() + "!!");
                player.getItems().add(item);
                break;
            case 2:
                ConsoleManager.print(ConsoleManager.CYAN, FOUND_NOTHING);
                break;
            case 3:
                ConsoleManager.print(ConsoleManager.CYAN, FOUND_ENEMY);
                this.fightEvents.fight(stageMap, player);
                break;
        }
    }

    @Override
    public boolean move(StageMap stageMap, Player player) {

        boolean keepMoving = true;

        int option = RandomEventGenerator.generateOption(0, 1);

        switch (option) {
            case 0:
                stageMap.setStepsToFinish(stageMap.getStepsToFinish() - 1);
                if (stageMap.getStepsToFinish() > 0) {
                    ConsoleManager.print(ConsoleManager.GREEN, "You're " + stageMap.getStepsToFinish() + " steps away from exit");
                } else {
                    keepMoving = false;
                }

                break;
            case 1:
                ConsoleManager.print(ConsoleManager.CYAN, FOUND_ENEMY);
                this.fightEvents.fight(stageMap, player);
                break;
        }

        return keepMoving;
    }

    @Override
    public void setItemGenerator(ItemGenerator strangerStuffItemGenerator) {
        this.itemGenerator = strangerStuffItemGenerator;
    }

    @Override
    public void setFightEvents(FightEvents fightEvents) {
        this.fightEvents = fightEvents;
    }
}
