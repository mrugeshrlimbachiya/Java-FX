package com.fx;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public static ImageView createResponsiveImageView(String path, Button button) {
        ImageView imageView = new ImageView(new Image(path));
        imageView.fitWidthProperty().bind(button.widthProperty().multiply(0.7));
        imageView.fitHeightProperty().bind(button.heightProperty().multiply(0.7));
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
