package com.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Step 1: Create GameBoard (don’t pass null)
        GameBoard board = new GameBoard();

        // Step 2: Create Scene using board root
        Scene scene = new Scene(board.getRoot(), 700, 800);

        // Step 3: Set scene in board
        board.setScene(scene);

        // Step 4: Configure stage
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();

        // Step 5: Make root responsive
        board.getRoot().prefWidthProperty().bind(scene.widthProperty());
        board.getRoot().prefHeightProperty().bind(scene.heightProperty());
    }

    public static void main(String[] args) {
        launch();
    }
}
