package nl.abdel.aoc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BingoGame {

    public static final String DOUBLE_LINE_SEPARATOR = System.lineSeparator() + System.lineSeparator();
    private LinkedList<Integer> luckyNumbers = new LinkedList<>();
    private final List<BingoBoard> boards = new LinkedList<>();

    private int nextLuckyNumberIndex;

    public BingoGame(final String numbers) {
        final List<String> numberParts = Arrays.asList(numbers.split(DOUBLE_LINE_SEPARATOR));
        this.luckyNumbers = Arrays
                .stream(numberParts.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
        createBoards(numberParts.subList(1, numberParts.size()));
    }

    private void createBoards(final List<String> boardStrings) {
        for (final String boardString : boardStrings) {
            boards.add(new BingoBoard(boardString));
        }
    }

    public BingoBoard getBoard(final int boardNumber) {
        return boards.get(boardNumber);
    }

    public void drawNumber() {
        for (final BingoBoard board : boards) {
            board.drawNumber(luckyNumbers.get(nextLuckyNumberIndex));
        }

        nextLuckyNumberIndex++;
    }

    public boolean isFinished() {
        return nextLuckyNumberIndex == luckyNumbers.size() || boards.stream().anyMatch(BingoBoard::hasBingo);
    }

    public int getNumberOfPlayers() {
        return boards.size();
    }

    public Optional<BingoBoard> getWinningBoard() {
        for (final BingoBoard board : boards) {
            if (board.hasBingo()) {
                return Optional.of(board);
            }
        }

        return Optional.empty();
    }

    public static class BingoBoard {
        private final List<List<Integer>> numbers = new LinkedList<>();
        private final List<List<Boolean>> markedNumbers = new LinkedList<>();
        private int lastNumberCalled;

        public BingoBoard(final String boardString) {
            final String[] rows = boardString.split(System.lineSeparator());
            for (final String row : rows) {
                final String[] numberStrings = row.split(" ");
                numbers.add(
                        Arrays.stream(numberStrings)
                                .filter(numberString -> !numberString.isEmpty())
                                .map(Integer::parseInt)
                                .collect(Collectors.toCollection(LinkedList::new))
                );
                markedNumbers.add(
                        Arrays.stream(numberStrings)
                                .filter(numberString -> !numberString.isEmpty())
                                .map(n -> false)
                                .collect(Collectors.toCollection(LinkedList::new))
                );
            }
        }

        public int[][] getNumbers() {
            final int rows = numbers.size();
            final int[][] numberGrid = new int[rows][];

            for (int row = 0; row < rows; row++) {
                final int columns = numbers.get(row).size();
                final int[] rowNumbers = new int[columns];
                for (int column = 0; column < columns; column++) {
                    rowNumbers[column] = numbers.get(row).get(column);
                }
                numberGrid[row] = rowNumbers;
            }

            return numberGrid;
        }

        public boolean[][] getMarkedNumbers() {
            final int rows = markedNumbers.size();
            final boolean[][] markedGrid = new boolean[rows][];

            for (int row = 0; row < rows; row++) {
                final int columns = markedNumbers.get(row).size();
                final boolean[] rowNumbers = new boolean[columns];
                for (int column = 0; column < columns; column++) {
                    rowNumbers[column] = markedNumbers.get(row).get(column);
                }
                markedGrid[row] = rowNumbers;
            }

            return markedGrid;
        }

        public boolean hasBingo() {
            for (int row = 0; row < numbers.size(); row++) {
                final boolean rowIsMarked = markedNumbers.get(row).stream().allMatch(isMarked -> isMarked == Boolean.TRUE);
                if (rowIsMarked) {
                    return true;
                }
                for (int column = 0; column < numbers.get(row).size(); column++) {
                    final int finalColumn = column;
                    final boolean columnIsMarked = markedNumbers.stream().allMatch(markedRow -> markedRow.size() <= finalColumn || markedRow.get(finalColumn) == Boolean.TRUE);
                    if (columnIsMarked) {
                        return true;
                    }
                }
            }

            return false;
        }

        public int getScore() {
            if (!hasBingo()) {
                return 0;
            }

            int sumOfUnmarkedNumbers = 0;
            for (int row = 0; row < markedNumbers.size(); row++) {
                for (int column = 0; column < markedNumbers.get(row).size(); column++) {
                    if (markedNumbers.get(row).get(column) == Boolean.FALSE) {
                        sumOfUnmarkedNumbers += numbers.get(row).get(column);
                    }
                }
            }

            return sumOfUnmarkedNumbers * lastNumberCalled;
        }

        private void drawNumber(final Integer number) {
            lastNumberCalled = number;
            for (final List<Integer> row : numbers) {
                if (!row.contains(number)) {
                    continue;
                }

                final int indexOfRow = numbers.indexOf(row);
                final int indexOfNumber = row.indexOf(number);
                markedNumbers.get(indexOfRow).set(indexOfNumber, Boolean.TRUE);
            }
        }
    }
}
