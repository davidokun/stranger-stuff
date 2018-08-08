package com.stranger.stage;

import com.stranger.util.LoadAsset;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Model class for map within a stage
 */
public class StageMap {

    private String mapName;
    private int stepsToFinish;
    private transient ByteArrayOutputStream graphicArea;

    public String getMapName() {
        return mapName;
    }

    public int getStepsToFinish() {
        return stepsToFinish;
    }

    public void setStepsToFinish(int stepsToFinish) {
        this.stepsToFinish = stepsToFinish;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * Gets a new InputStream to print the map
     * @return
     */
    public InputStream getGraphicArea() {
        return new ByteArrayInputStream(this.graphicArea.toByteArray());
    }

    /**
     * Set the stage map as a ByteArrayOutputStream to be reused to print map on
     * console.
     * @param graphicArea the graphic area of the current stage
     */
    public void setGraphicArea(InputStream graphicArea) {

        this.graphicArea = LoadAsset.convertToByteArray(graphicArea);
    }
}
