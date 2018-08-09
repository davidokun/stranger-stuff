package com.stranger;

/**
 * This is the initial point of access of the game
 */
public class Game {

    public static void main(String[] args) {

        GameDirector gameDirector = new GameDirector();
        gameDirector.gameFlow();

    }
}
