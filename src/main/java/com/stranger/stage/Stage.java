package com.stranger.stage;

import java.util.List;

/**
 * Model class for a game Stage
 */
public class Stage {

    private String stageName;
    private List<StageMap> maps;

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public List<StageMap> getMaps() {
        return maps;
    }

    public void setMaps(List<StageMap> maps) {
        this.maps = maps;
    }

}
