package com.fx;

import javafx.scene.Scene;

public class ThemeManager {

    public static void applyLightTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeManager.class.getResource("/styles/light-theme.css").toExternalForm());
    }

    public static void applyDarkTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeManager.class.getResource("/styles/dark-theme.css").toExternalForm());
    }
}
