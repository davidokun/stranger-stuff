package com.stranger;

import com.stranger.util.ConsoleColor;
import com.stranger.util.LoadAsset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This is the initial point of access of the game
 */
public class Game {

    public static void main(String[] args) {

        LoadAsset loadAsset = new LoadAsset();
        Optional<Path> title = loadAsset.loadAsset("stranger-stuff/title.txt");

        if (title.isPresent()) {
            try (Stream<String> stream = Files.lines(title.get())) {
                stream.forEach(l -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(ConsoleColor.RED + l + ConsoleColor.RESET);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
