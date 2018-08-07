package com.stranger.util;

import com.stranger.player.Player;
import com.stranger.player.impl.GamePlayer;
import com.stranger.stage.Stage;
import com.stranger.stage.StageMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class to load assets (text files) from the class path.
 */
public class LoadAsset {

    public static final String[] gameConfig = new String[2];
    public static final String[] contextConfig = new String[6];

    public void loadGameConfig(String[] config, String fileName) {
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {
                config[index++] = line;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Optional<InputStream> loadAsset(String folderGame, String assetName) {

        return Optional.of(getClass().getResourceAsStream("/assets/" + folderGame + assetName));
    }

    /**
     * This method is used to recreate the InputStream of the asset to re-print it in the screen
     *
     * @param inputStream the <code>{@link InputStream}</code> for the asset
     * @return the <code>ByteArrayOutputStream</code> to re-print the asset
     */
    public static ByteArrayOutputStream convertToByteArray(InputStream inputStream) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        try {
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }

            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos;
    }

    public static InputStream recreateAsset(InputStream inputStream) {

        ByteArrayOutputStream assetArray = convertToByteArray(inputStream);
        return new ByteArrayInputStream(assetArray.toByteArray());
    }


    public List<Stage> loadStageAssets(String folderGame) {

        List<Stage> strangerStages = new ArrayList<>();

        String[] stagesToLoad = LoadAsset.gameConfig[0].split(",");

        int mapsPerStage = 2;

        for (String stage : stagesToLoad) {
            List<StageMap> maps = new ArrayList<>();
            Stage st = new Stage();
            st.setStageName(stage);

            for (int i = 1; i <= mapsPerStage; i++) {

                StageMap stageMap = new StageMap();
                stageMap.setStepsToFinish(i);
                stageMap.setMapName(stage.toLowerCase() + "-" + i);

                loadAsset(folderGame, "/stages/" + stage.toLowerCase() + "/maps/"+ stage.toLowerCase() + "-" + i + ".txt")
                        .ifPresent(stageMap::setGraphicArea);
                maps.add(stageMap);
            }

            st.setMaps(maps);
            strangerStages.add(st);
        }

        return strangerStages;
    }

    public List<Player> loadEnemyAssets() {

        List<Player> enemies;

        String[] enemiesToLoad = LoadAsset.gameConfig[1].split("\\|");
        enemies = new ArrayList<>();

        for (String anEnemiesToLoad : enemiesToLoad) {

            String[] enemyToLoad = anEnemiesToLoad.split(",");


            GamePlayer enemy = new GamePlayer(enemyToLoad[0], enemyToLoad[1]);
            enemy.setWeapon(enemyToLoad[2]);
            enemy.setExperience(Integer.parseInt(enemyToLoad[3]));
            enemy.setLife(Integer.parseInt(enemyToLoad[4]));
            enemies.add(enemy);
        }

        return enemies;
    }
}
