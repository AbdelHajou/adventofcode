package nl.abdel.aoc.twentyone;

import nl.abdel.aoc.InputHelper;
import nl.abdel.aoc.twentyone.VentScanner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(5)
class VentScannerTest {

    private static String exampleInput;

    private static final int[][] firstExampleDiagram = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 2, 1, 1, 1, 2, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 1, 1, 1, 0, 0, 0, 0},
    };

    private static final int[][] secondExampleDiagram = new int[][]{
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 2, 0, 0},
            {0, 0, 2, 0, 1, 0, 1, 1, 1, 0},
            {0, 0, 0, 1, 0, 2, 0, 2, 0, 0},
            {0, 1, 1, 2, 3, 1, 3, 2, 1, 1},
            {0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {2, 2, 2, 1, 1, 1, 0, 0, 0, 0},
    };

    private VentScanner ventScanner;

    private static String puzzleInput;

    @BeforeAll
    static void readInputFiles() throws IOException {
        exampleInput = InputHelper.readFileToString("2021/hydro_vents_example.txt");
        puzzleInput = InputHelper.readFileToString("2021/hydro_vents.txt");
    }

    @BeforeEach
    void setUp() throws IOException {
        ventScanner = new VentScanner();
    }

    @Test
    void shouldScanHorizontalLineSegment() {
        final var expectedDiagram = new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 1}
        };
        final var lineSegment = "5,0 -> 8,0";

        final var actualDiagram = ventScanner.scanLineSegments(lineSegment, false);

        assertArrayEquals(expectedDiagram, actualDiagram);
    }

    @Test
    void shouldScanVerticalLineSegment() {
        final var expectedDiagram = new int[][]{
                {0},
                {1},
                {1},
                {1},
                {1},
                {1}
        };
        final var lineSegment = "0,1 -> 0,5";

        final var actualDiagram = ventScanner.scanLineSegments(lineSegment, false);

        assertArrayEquals(expectedDiagram, actualDiagram);
    }

    @Test
    void shouldSolveExampleDiagram() {
        final var actualDiagram = ventScanner.scanLineSegments(exampleInput, false);

        assertArrayEquals(firstExampleDiagram, actualDiagram);
    }

    @Test
    void shouldSolvePuzzleOne() {
        final var expectedNumberOfOverlappingPoints = 6548;

        final var actualDiagram = ventScanner.scanLineSegments(puzzleInput, false);
        final var actualNumberOfOverlappingPoints = findNumberOfOverlappingPoints(actualDiagram);

        assertEquals(expectedNumberOfOverlappingPoints, actualNumberOfOverlappingPoints);
        System.out.println("Day five part one solution: " + expectedNumberOfOverlappingPoints);
    }

    @Test
    void shouldScanDiagonalLines() {
        final var actualDiagram = ventScanner.scanLineSegments(exampleInput, true);

        assertArrayEquals(secondExampleDiagram, actualDiagram);
    }

    @Test
    void shouldSolvePuzzleTwo() {
        final var expectedNumberOfOverlappingPoints = 19663;

        final var actualDiagram = ventScanner.scanLineSegments(puzzleInput, true);
        final var actualNumberOfOverlappingPoints = findNumberOfOverlappingPoints(actualDiagram);

        assertEquals(expectedNumberOfOverlappingPoints, actualNumberOfOverlappingPoints);
        System.out.println("Day five part two solution: " + expectedNumberOfOverlappingPoints);
    }

    private int findNumberOfOverlappingPoints(final int[][] diagram) {
        int numberOfOverlappingPoints = 0;

        for (final int[] row : diagram) {
            for (final int point : row) {
                if (point >= 2) {
                    numberOfOverlappingPoints++;
                }
            }
        }

        return numberOfOverlappingPoints;
    }
}
