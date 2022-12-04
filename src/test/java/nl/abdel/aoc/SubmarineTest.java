package nl.abdel.aoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Order(2)
class SubmarineTest {

    private static List<String> puzzleInput;

    @Nested
    class DefaultSubmarineTest {
        private Submarine defaultSubmarine = new Submarine();

        @BeforeEach
        void setUp() throws IOException {
            defaultSubmarine = new Submarine();
            puzzleInput = InputHelper.readLines("submarine_commands.txt");
        }

        @Test
        void shouldStartAtZeroZero() {
            assertEquals(0, defaultSubmarine.getPosition());
            assertEquals(0, defaultSubmarine.getDepth());
        }

        @Test
        void shouldMoveForwardByXUnits() {
            final var expectedPosition = 5;

            defaultSubmarine.command("forward 5");

            assertEquals(expectedPosition, defaultSubmarine.getPosition());
        }

        @Test
        void shouldMoveDownByXUnits() {
            final var expectedDepth = 5;

            defaultSubmarine.command("down 5");

            assertEquals(expectedDepth, defaultSubmarine.getDepth());
        }

        @Test
        void shouldMoveUpByXUnits() {
            final var submarineAtDepth5 = new Submarine(0, 5);
            final var expectedDepth = 2;

            submarineAtDepth5.command("up 3");

            assertEquals(expectedDepth, submarineAtDepth5.getDepth());
        }

        @Test
        void shouldEndUpAtPosition15AndDepth10() {
            final var expectedPosition = 15;
            final var expectedDepth = 10;

            defaultSubmarine.command("forward 5");
            defaultSubmarine.command("down 5");
            defaultSubmarine.command("forward 8");
            defaultSubmarine.command("up 3");
            defaultSubmarine.command("down 8");
            defaultSubmarine.command("forward 2");

            assertEquals(expectedPosition, defaultSubmarine.getPosition());
            assertEquals(expectedDepth, defaultSubmarine.getDepth());
        }

        @Test
        void shouldSolvePuzzleOne() {
            final var expectedPosition = 2050;
            final var expectedDepth = 826;
            final var expectedProduct = expectedPosition * expectedDepth;

            for (final String command : puzzleInput) {
                defaultSubmarine.command(command);
            }

            assertEquals(expectedPosition, defaultSubmarine.getPosition());
            assertEquals(expectedDepth, defaultSubmarine.getDepth());
            final var actualProduct = defaultSubmarine.getPosition() * defaultSubmarine.getDepth();
            assertEquals(expectedProduct, actualProduct);
            System.out.println("Day two part one solution: " + actualProduct);
        }

        @Test
        void shouldHandleNullCorrectly() {
            final var expectedException = IllegalArgumentException.class;

            assertThrows(expectedException, () -> {
                defaultSubmarine.command(null);
            });
        }
    }

    @Nested
    class AimingSubmarineTest {
        private AimingSubmarine aimingSubmarine = new AimingSubmarine();

        @BeforeEach
        void setUp() throws IOException {
            aimingSubmarine = new AimingSubmarine();
            puzzleInput = InputHelper.readLines("submarine_commands.txt");
        }

        @Test
        void aimShouldStartAtZero() {
            assertEquals(0, aimingSubmarine.getAim());
        }

        @Test
        void downIncreasesAimByXUnits() {
            final var expectedAim = 5;

            aimingSubmarine.command("down 5");

            assertEquals(expectedAim, aimingSubmarine.getAim());
        }

        @Test
        void upIncreasesAimByXUnits() {
            final var aimingSubmarineWithAim5 = new AimingSubmarine(0, 0, 5);
            final var expectedAim = 2;

            aimingSubmarineWithAim5.command("up 3");

            assertEquals(expectedAim, aimingSubmarineWithAim5.getAim());
        }

        @Test
        void forwardIncreasesPositionAndDepth() {
            final var expectedPosition = 13;
            final var expectedDepth = 40;
            final var expectedAim = 5;

            aimingSubmarine.command("forward 5");
            aimingSubmarine.command("down 5");
            aimingSubmarine.command("forward 8");

            assertEquals(expectedPosition, aimingSubmarine.getPosition());
            assertEquals(expectedDepth, aimingSubmarine.getDepth());
            assertEquals(expectedAim, aimingSubmarine.getAim());
        }

        @Test
        void shouldEndUpAtPosition15AndDepth60() {
            final var expectedPosition = 15;
            final var expectedDepth = 60;

            aimingSubmarine.command("forward 5");
            aimingSubmarine.command("down 5");
            aimingSubmarine.command("forward 8");
            aimingSubmarine.command("up 3");
            aimingSubmarine.command("down 8");
            aimingSubmarine.command("forward 2");

            assertEquals(expectedPosition, aimingSubmarine.getPosition());
            assertEquals(expectedDepth, aimingSubmarine.getDepth());
        }

        @Test
        void shouldSolvePuzzleTwo() {
            final var expectedPosition = 2050;
            final var expectedDepth = 906321;
            final var expectedAim = 826;
            final var expectedProduct = expectedPosition * expectedDepth;

            for (final String command : puzzleInput) {
                aimingSubmarine.command(command);
            }

            assertEquals(expectedPosition, aimingSubmarine.getPosition());
            assertEquals(expectedDepth, aimingSubmarine.getDepth());
            assertEquals(expectedAim, aimingSubmarine.getAim());
            final var actualProduct = aimingSubmarine.getPosition() * aimingSubmarine.getDepth();
            assertEquals(expectedProduct, actualProduct);
            System.out.println("Day two part two solution: " + actualProduct);
        }
    }
}
