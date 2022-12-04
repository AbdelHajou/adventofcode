package nl.abdel.aoc.twentyone;

import nl.abdel.aoc.InputHelper;
import nl.abdel.aoc.twentyone.SonarSweeper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(1)
class SonarSweeperTest {

    private final SonarSweeper sonarSweeper = new SonarSweeper();

    private static int[] puzzleInput;

    @BeforeAll
    static void setUp() throws IOException {
        puzzleInput = InputHelper.readLinesToIntegers("2021/depth_measurements.txt");
    }

    @ParameterizedTest
    @MethodSource("provideMeasurementsForPuzzleOne")
    void shouldCountCorrectNumberOfLinearIncreasements(final int[] depthMeasurements, final int expected) {
        final var actual = sonarSweeper.countDepthMeasurementIncrements(depthMeasurements);

        assertEquals(expected, actual);
        if (depthMeasurements == puzzleInput) {
            System.out.println("Day one part one solution: " + expected);
        }
    }

    @ParameterizedTest
    @MethodSource("provideMeasurementsForPuzzleTwo")
    void shouldCountCorrectNumberOfSlidingWindowIncreasements(final int[] depthMeasurements, final int expected) {
        final var actual = sonarSweeper.countSlidingWindowIncrements(depthMeasurements, 3);

        assertEquals(expected, actual);
        if (depthMeasurements == puzzleInput) {
            System.out.println("Day one part two solution: " + expected);
        }
    }

    private static Stream<Arguments> provideMeasurementsForPuzzleOne() {
        return Stream.of(
                Arguments.of(new int[]{ 199, 200, 208, 210, 200, 207, 240, 269, 260, 263 }, 7),
                Arguments.of(new int[]{ }, 0),
                Arguments.of(new int[]{ 199 }, 0),
                Arguments.of(new int[]{ 199, 199 }, 0),
                Arguments.of(puzzleInput, 1292)
        );
    }

    private static Stream<Arguments> provideMeasurementsForPuzzleTwo() {
        return Stream.of(
                Arguments.of(new int[]{ 199, 200, 208, 210, 200, 207, 240, 269, 260, 263 }, 5),
                Arguments.of(new int[]{ }, 0),
                Arguments.of(new int[]{ 199, 200 }, 0),
                Arguments.of(new int[]{ 199, 200, 208 }, 0),
                Arguments.of(new int[]{ 199, 200, 208, 210 }, 1),
                Arguments.of(puzzleInput, 1262)
        );
    }
}