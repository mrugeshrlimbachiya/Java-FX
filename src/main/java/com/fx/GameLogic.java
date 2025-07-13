package com.fx;

import javafx.scene.control.TextInputDialog;
import java.util.Arrays;

public class GameLogic {
    private final int[] gameState = new int[9];
    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    private int activePlayer = 0;
    private boolean gameOver = false;
    private String playerOName = "Player O";
    private String playerXName = "Player X";
    private int playerOScore = 0;
    private int playerXScore = 0;
    private int[] winningLine = new int[3];

    public void promptPlayerNames() {
        TextInputDialog dialogO = new TextInputDialog("Player O");
        dialogO.setTitle("Enter Player Name");
        dialogO.setHeaderText("Enter name for O");
        dialogO.setContentText("Player O:");
        dialogO.showAndWait().ifPresent(name -> playerOName = name.trim().isEmpty() ? "Player O" : name.trim());

        TextInputDialog dialogX = new TextInputDialog("Player X");
        dialogX.setTitle("Enter Player Name");
        dialogX.setHeaderText("Enter name for X");
        dialogX.setContentText("Player X:");
        dialogX.showAndWait().ifPresent(name -> playerXName = name.trim().isEmpty() ? "Player X" : name.trim());
    }

    public boolean isValidMove(int index) {
        return !gameOver && gameState[index] == 3;
    }

    public boolean checkWinner() {
        for (int[] wp : winningPositions) {
            if (gameState[wp[0]] == gameState[wp[1]] &&
                    gameState[wp[1]] == gameState[wp[2]] &&
                    gameState[wp[0]] != 3) {
                gameOver = true;
                winningLine = wp;
                if (gameState[wp[0]] == 0) playerOScore++; else playerXScore++;
                return true;
            }
        }
        return false;
    }

    public boolean isDraw() {
        if (gameOver) return false;
        for (int cell : gameState) {
            if (cell == 3) return false;
        }
        gameOver = true;
        return true;
    }

    public void switchTurn() {
        activePlayer = 1 - activePlayer;
    }

    public void restartGame() {
        Arrays.fill(gameState, 3);
        gameOver = false;
        activePlayer = 0;
    }

    public void resetAll() {
        restartGame();
        playerOScore = 0;
        playerXScore = 0;
        promptPlayerNames();
    }

    public void resetCell(int index) {
        gameState[index] = 3;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public int[] getWinningLine() {
        return winningLine;
    }

    public String getWinnerName() {
        return gameState[winningLine[0]] == 0 ? playerOName : playerXName;
    }

    public String getCurrentPlayerName() {
        return activePlayer == 0 ? playerOName : playerXName;
    }

    public String getScoreDisplay() {
        return playerOName + ": " + playerOScore + "  |  " + playerXName + ": " + playerXScore;
    }

    public void markMove(int index, int player) {
        gameState[index] = player;
    }

}
