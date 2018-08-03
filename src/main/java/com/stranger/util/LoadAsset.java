package com.stranger.util;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class LoadAsset {

    public Optional<Path> loadAsset(String assetName) {

        try {
            return Optional.of(Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("assets/" + assetName)).toURI()));
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }
}
