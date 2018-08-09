package com.stranger.events.impl;

import com.stranger.events.FightEvents;
import com.stranger.gameplay.impl.DefaultGamePlay;
import com.stranger.player.Player;
import com.stranger.stage.StageMap;
import com.stranger.util.ConsoleManager;
import com.stranger.util.LoadAsset;
import com.stranger.util.RandomEventGenerator;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class DefaultGamePlayFightEvents implements FightEvents {

    private LoadAsset loadAsset;
    private List<Player> enemies;

    @Override
    public void loadEnemies() {
        enemies = loadAsset.loadEnemyAssets();
    }


    @Override
    public void fight(StageMap stageMap, Player player) {

        int enemyIndex = RandomEventGenerator.generateOption(0, enemies.size() - 1);

        Player enemy = enemies.get(enemyIndex);
        int life = enemy.getLife();

        Optional<InputStream> fightAsset = loadAsset.loadAsset(DefaultGamePlay.folderGame, String.format("/fight/%s.txt", enemy.getName().toLowerCase()));

        if (fightAsset.isPresent()) {

            boolean fightIsOn = true;

            while (fightIsOn) {
                ConsoleManager.printScrollableText(ConsoleManager.RED, LoadAsset.recreateAsset(fightAsset.get()), 100);
                ConsoleManager.print(ConsoleManager.CYAN, "Select an action:\n1. Attack\n2. Leave");
                String action = ConsoleManager.read();


                switch (action) {
                    case "1":
                        characterTurn(player, enemy, true);
                        characterTurn(player, enemy, false);
                        if (enemy.getLife() <= 0) {
                            ConsoleManager.print(ConsoleManager.RED, "You have defeated " + enemy.getName() + " !!!!!");
                            ConsoleManager.print(ConsoleManager.GREEN, "You have receive " + enemy.getExperience() + " exp points");
                            player.setExperience(player.getExperience() + enemy.getExperience());
                            enemy.setLife(life);
                            fightIsOn = false;
                        }
                        break;
                    case "2":
                        fightIsOn = false;
                        break;
                    default:
                        ConsoleManager.print(ConsoleManager.RED, "Please select an option");
                }
            }

        }

    }

    @Override
    public void characterTurn(Player player, Player enemy, boolean isPlayer) {

        if (isPlayer) {
            ConsoleManager.print(ConsoleManager.GREEN, player.getName() + " Attack with his " + player.getWeapon());
            enemy.setLife(enemy.getLife() - 5);
        } else {
            ConsoleManager.print(ConsoleManager.RED, enemy.getName() + " Attacks you with his " + enemy.getWeapon());
        }

    }

    @Override
    public void setLoadAsset(LoadAsset loadAsset) {
        this.loadAsset = loadAsset;
    }
}
