package com.fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameBoard {

    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final Label title = new Label("Tic Tac Toe Game");

    private final Button restartButton = new Button("Restart Game");
    private final Button resetAllButton = new Button("Reset All");
    private final Label turnLabel = new Label();
    private final Label scoreLabel = new Label();
    private final ToggleButton themeToggle = new ToggleButton("Dark Mode");

    private final Font font = Font.font("Roboto", FontWeight.BOLD, 40);
    private final Font labelFont = Font.font("Roboto", FontWeight.NORMAL, 25);

    private final Button[] btns = new Button[9];
    private final GameLogic logic = new GameLogic();

    private Scene scene;

    public GameBoard() {
        setupLayout();
        setupButtons();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        ThemeManager.applyLightTheme(scene); // Apply theme after scene is set
        setupEvents(scene);                  // Now safe to bind events
    }

    public BorderPane getRoot() {
        return borderPane;
    }

    private void setupLayout() {
        title.setFont(font);
        restartButton.setFont(labelFont);
        resetAllButton.setFont(labelFont);

        turnLabel.setFont(labelFont);
        scoreLabel.setFont(labelFont);
        turnLabel.setTextFill(Color.DARKBLUE);

        logic.promptPlayerNames();
        updateLabels();

        VBox bottomBox = new VBox(15, turnLabel, scoreLabel, themeToggle, restartButton, resetAllButton);
        bottomBox.setAlignment(Pos.CENTER);

        borderPane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        borderPane.setBottom(bottomBox);
        borderPane.setPadding(new Insets(20));

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 9; i++) {
            Button btn = new Button();
            btn.setId(String.valueOf(i));
            btn.setFont(font);
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            gridPane.add(btn, i % 3, i / 3);
            btns[i] = btn;
            logic.resetCell(i);
        }

        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / 3);
            gridPane.getColumnConstraints().add(cc);

            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / 3);
            gridPane.getRowConstraints().add(rc);
        }

        borderPane.setCenter(gridPane);
    }

    private void setupButtons() {
        for (int i = 0; i < btns.length; i++) {
            int index = i;
            btns[i].setOnAction(e -> handleMove(index));
        }
    }

    private void setupEvents(Scene scene) {
        restartButton.setOnAction(e -> {
            logic.restartGame();
            resetButtons();
            updateLabels();
        });

        resetAllButton.setOnAction(e -> {
            logic.resetAll();
            resetButtons();
            updateLabels();
        });

        themeToggle.setOnAction(e -> {
            if (themeToggle.isSelected()) {
                ThemeManager.applyDarkTheme(scene);
                themeToggle.setText("Light Mode");
            } else {
                ThemeManager.applyLightTheme(scene);
                themeToggle.setText("Dark Mode");
            }
        });
    }

    private void handleMove(int index) {
        if (!logic.isValidMove(index)) {
            Utils.showAlert(Alert.AlertType.WARNING, "Invalid Move", "This cell is already occupied or the game is over.");
            return;
        }

        int player = logic.getActivePlayer();
        logic.markMove(index, player); // <-- This is the fix!
        String imagePath = player == 0 ? "file:src/main/resources/images/zero.png" : "file:src/main/resources/images/cross.png";
        btns[index].setGraphic(Utils.createResponsiveImageView(imagePath, btns[index]));

        if (logic.checkWinner()) {
            for (int i : logic.getWinningLine()) {
                btns[i].setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            Utils.showAlert(Alert.AlertType.INFORMATION, "Game Over", logic.getWinnerName() + " has won!");
        } else if (logic.isDraw()) {
            Utils.showAlert(Alert.AlertType.INFORMATION, "Draw", "It's a draw!");
        } else {
            logic.switchTurn();
        }
        updateLabels();
    }

    private void resetButtons() {
        for (int i = 0; i < 9; i++) {
            logic.resetCell(i);
            btns[i].setGraphic(null);
            btns[i].setBackground(null);
            btns[i].setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        }
    }

    private void updateLabels() {
        turnLabel.setText("Turn: " + logic.getCurrentPlayerName());
        scoreLabel.setText(logic.getScoreDisplay());
    }
}
