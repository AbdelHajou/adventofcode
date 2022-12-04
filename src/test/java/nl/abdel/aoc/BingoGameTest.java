package nl.abdel.aoc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Order(4)
class BingoGameTest {

    private static String exampleGame;

    private static String irregularGridsGame;

    private static String puzzleGame;

    @BeforeAll
    static void readInputFiles() throws IOException {
        exampleGame = InputHelper.readFileToString("bingo_game_example.txt");
        irregularGridsGame = InputHelper.readFileToString("bingo_game_irregular.txt");
        puzzleGame = InputHelper.readFileToString("bingo_game.txt");
    }

    private static final int[][] FIRST_BOARD_NUMBERS = new int[][] {
            {22, 13, 17, 11, 0},
            {8, 2, 23, 4, 24},
            {21, 9, 14, 16, 7},
            {6, 10, 3, 18, 5},
            {1, 12, 20, 15, 19}
    };

    @Test
    void shouldCreateBingoBoards() {
        var expectedNumberOfBoards = 3;

        var bingoGame = new BingoGame(exampleGame);

        var actualNumberOfBoards = bingoGame.getNumberOfPlayers();
        assertEquals(expectedNumberOfBoards, actualNumberOfBoards);
    }

    @Test
    void shouldParseBoards() {
        var expectedNumbers = FIRST_BOARD_NUMBERS;

        var bingoGame = new BingoGame(exampleGame);

        var actualNumbers = bingoGame.getBoard(0).getNumbers();
        assertArrayEquals(expectedNumbers, actualNumbers);
    }

    @Test
    void shouldStartWithNoNumbersMarked() {
        var expectedMarkedNumbers = new boolean[5][5];

        var bingoGame = new BingoGame(exampleGame);

        var actualMarkedNumbers = bingoGame.getBoard(0).getMarkedNumbers();
        assertArrayEquals(expectedMarkedNumbers, actualMarkedNumbers);
    }

    @Test
    void shouldDrawAndMarkNumbers() {
        var bingoGame = new BingoGame(exampleGame);
        var firstBoard = bingoGame.getBoard(0);

        bingoGame.drawNumber();

        boolean numberIsMarked = firstBoard.getMarkedNumbers()[2][4];
        assertTrue(numberIsMarked);
    }

    @Test
    void thirdBoardShouldHaveBingoAfterDrawingTwelveNumbers() {
        var expectedScore = 4512;
        var numbersToDraw = new int[]{7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24};
        var bingoGame = new BingoGame(exampleGame);
        var thirdBoard = bingoGame.getBoard(2);

        for (int numberToDraw : numbersToDraw) {
            bingoGame.drawNumber();
        }

        assertTrue(thirdBoard.hasBingo());
        assertEquals(expectedScore, thirdBoard.getScore());
        assertTrue(bingoGame.isFinished());
    }

    @Test
    void shouldSolvePuzzleOne() {
        var expectedScore = 58838;
        var bingoGame = new BingoGame(puzzleGame);

        while (!bingoGame.isFinished()) {
            bingoGame.drawNumber();
        }

        var winningBoard = bingoGame.getWinningBoard().get();
        assertEquals(expectedScore, winningBoard.getScore());
        System.out.println("Day four part one solution: " + winningBoard.getScore());
    }

    @Test
    void shouldSolvePuzzleTwo() {
        var expectedScore = 6256;
        var bingoGame = new BingoGame(puzzleGame);

        while (numberOfPlayersWithBingo(bingoGame) < bingoGame.getNumberOfPlayers() - 1) {
            bingoGame.drawNumber();
            if (numberOfPlayersWithBingo(bingoGame) == bingoGame.getNumberOfPlayers() - 1) {
                var losingBoard = findLosingBoard(bingoGame);
                while (!losingBoard.hasBingo()) {
                    bingoGame.drawNumber();
                }
                assertEquals(expectedScore, losingBoard.getScore());
                System.out.println("Day four part two solution: " + losingBoard.getScore());
                return;
            }
        }

        fail();
    }

    @Test
    void shouldNotHaveWinningBoardWhenNoBoardsHaveBingo() {
        var bingoGame = new BingoGame(exampleGame);

        while (numberOfPlayersWithBingo(bingoGame) == 0) {
            assertTrue(bingoGame.getWinningBoard().isEmpty());
            bingoGame.drawNumber();
        }

        assertTrue(bingoGame.getWinningBoard().isPresent());
    }

    @Test
    void shouldWorkWithIrregularGrids() {
        var expectedScore = 1044;
        var bingoGame = new BingoGame(irregularGridsGame);

        while (!bingoGame.isFinished()) {
            bingoGame.drawNumber();
        }

        var winningBoard = bingoGame.getWinningBoard().get();
        assertEquals(expectedScore, winningBoard.getScore());
    }

    private static int numberOfPlayersWithBingo(BingoGame bingoGame) {
        int numberOfPlayersWithBingo = 0;
        for (int i = 0; i < bingoGame.getNumberOfPlayers(); i++) {
            if (bingoGame.getBoard(i).hasBingo()) numberOfPlayersWithBingo++;
        }

        return numberOfPlayersWithBingo;
    }

    private static BingoGame.BingoBoard findLosingBoard(BingoGame bingoGame) {
        for (int i = 0; i < bingoGame.getNumberOfPlayers(); i++) {
            if (!bingoGame.getBoard(i).hasBingo()) return bingoGame.getBoard(i);
        }

        return null;
    }
}
