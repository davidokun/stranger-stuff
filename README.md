# Stranger Stuff Game

A Role Playing game in Console

## Prerequisites

JRE = Java 8+

Maven 3.2+
2
## How To Run

* Extract the file `game-puzzle.zip`
* `cd` into `/game-puzzle`
* Run `mvn clean package`
* Run `java -jar target/stranger-stuff-1.0-SNAPSHOT.jar`

## How To Extend the Game

### Changing the Theme

If you want to change the theme just need to create the corresponding assets in /resources/assets folder.
Following the same structure that already exists for each asset.

* intro
* music-setup
* title
* stages/
* fight/

Also need to configure the files `src/main/java/resources/game-config.txt` and `src/main/java/resources/game-context.tx`

To test a new theme (already created) just do the following:

* Replace the contents of `src/main/java/resources/game-config.txt` with:

```
City,Castle,Shinra
Malrboro,10,Putrid Breath,10,10|Sephirot,45,Meteor,20,20
```

* In `src/main/java/resources/game-context.txt` replace the `folderGame` attribute with:

* `folderGame=final-fantasy`

* Compile again with `mvn clean package`

* Run `java -jar target/stranger-stuff-1.0-SNAPSHOT.jar`

* You should see a brand new theme for the game.

### Implement new functionality

To Add more specific behavior for a game, new classes can be implemented. The game is base on several abstractions
so the implementer can change specific functionality of the game.

The implementation must be one or all of these:

```
<interface> GameStoryLine
<interface> ItemGenerator
<interface> FightEvents
<interface> ExplorationEvents
<interface> GameState
```

Once the implementation of these abstractions has been done, it need to be configured in the file `src/main/java/resources/game-context.txt`

```
GameStoryLine=com.stranger.gameplay.impl.DefaultGamePlay
ItemGenerator=com.stranger.items.impl.DefaultGamePlayItemGenerator
FightEvents=com.stranger.events.impl.DefaultGamePlayFightEvents
ExplorationEvents=com.stranger.events.impl.DefaultGamePlayExplorationEvents
GameState=com.stranger.state.impl.DefaultGamePlayState
```

Once configured, compile (`mvn clean package`) and run (`java -jar target/stranger-stuff-1.0-SNAPSHOT.jar`)


## Have Fun!!