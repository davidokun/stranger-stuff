package com.stranger.state.impl;

import com.stranger.player.Player;
import com.stranger.state.GameState;
import com.stranger.util.ConsoleManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DefaultGamePlayState implements GameState {


    private static final String PLAYER_SAVE_FILE = "playar-save.ser";
    private static final String STAGE_SAVE_FILE = "stage-save.ser";

    protected Player player;
    private StringBuilder stageSate = new StringBuilder();

    @Override
    public boolean save(int stageIndex, int mapIndex, Player player) {

        try (FileOutputStream fout = new FileOutputStream(PLAYER_SAVE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {

            oos.writeObject(player);

            String stageState = stageIndex + "|" + mapIndex;

            Path path = Paths.get(STAGE_SAVE_FILE);
            byte[] strToBytes = stageState.getBytes();

            Files.write(path, strToBytes);

            return true;

        } catch (Exception e) {
            ConsoleManager.print(ConsoleManager.RED, "Opps an error occurred while saving your data");
            return false;
        }

    }

    @Override
    public GameState load() {

        try (FileInputStream streamIn = new FileInputStream(PLAYER_SAVE_FILE);
             ObjectInputStream objectinputstream = new ObjectInputStream(streamIn)) {

            this.player = (Player) objectinputstream.readObject();

            try (Stream<String> stream = Files.lines(Paths.get(STAGE_SAVE_FILE))) {

                stream.forEach(this.stageSate::append);
            }

        } catch (Exception e) {
            ConsoleManager.print(ConsoleManager.RED, "Opps an error occurred while loading your data");
        }

        return this;
    }


    public Player getPlayer() {
        return player;
    }

    public StringBuilder getStageSate() {
        return stageSate;
    }
}
