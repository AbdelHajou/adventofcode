package nl.abdel.aoc.twentyone;

import nl.abdel.aoc.InputHelper;
import nl.abdel.aoc.twentyone.EngineType;
import nl.abdel.aoc.twentyone.SubmarineFleet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(2021_7)
class SubmarineFleetTest {

    private static final String EXAMPLE_INPUT = "16,1,2,0,4,2,7,1,2,14";
    private static final int EXAMPLE_IDEAL_POSITION_WITH_NEW_ENGINES = 2;
    private static final int EXAMPLE_IDEAL_POSITION_WITH_OLD_ENGINES = 5;

    private SubmarineFleet fleet;

    private static String puzzleInput;

    @BeforeEach
    void setUp() throws IOException {
        puzzleInput = InputHelper.readFileToString("2021/crab_fleet.txt");
        fleet = new SubmarineFleet();
    }

    @Test
    void shouldFindIdealPosition() {
        fleet.spawnSubmarines(EXAMPLE_INPUT, EngineType.IMPROVED);

        final var actualIdealPosition = fleet.findIdealPosition();

        assertEquals(EXAMPLE_IDEAL_POSITION_WITH_NEW_ENGINES, actualIdealPosition);
    }

    @Test
    void shouldCalculateTotalFuel() {
        final var expectedTotalFuel = 37;
        fleet.spawnSubmarines(EXAMPLE_INPUT, EngineType.IMPROVED);

        final var actualTotalFuel = fleet.calculateTotalFuelSpentTowardsPosition(EXAMPLE_IDEAL_POSITION_WITH_NEW_ENGINES);

        assertEquals(expectedTotalFuel, actualTotalFuel);
    }

    @Test
    void shouldSolvePuzzleOne() {
        final var expectedIdealPosition = 298;
        final var expectedTotalFuel = 356958;
        fleet.spawnSubmarines(puzzleInput, EngineType.IMPROVED);

        final var actualIdealPosition = fleet.findIdealPosition();
        final var actualTotalFuel = fleet.calculateTotalFuelSpentTowardsPosition(actualIdealPosition);

        assertEquals(expectedIdealPosition, actualIdealPosition);
        assertEquals(expectedTotalFuel, actualTotalFuel);
        System.out.println("Day seven part one solution: " + actualTotalFuel);
    }

    @Test
    void shouldFindIdealPositionWithOldEngines() {
        fleet.spawnSubmarines(EXAMPLE_INPUT, EngineType.OLD);

        final var actualIdealPosition = fleet.findIdealPosition();

        assertEquals(EXAMPLE_IDEAL_POSITION_WITH_OLD_ENGINES, actualIdealPosition);
    }

    @Test
    void shouldCalculateTotalFuelWithOldEngines() {
        final var expectedTotalFuel = 168;
        fleet.spawnSubmarines(EXAMPLE_INPUT, EngineType.OLD);

        final var actualTotalFuel = fleet.calculateTotalFuelSpentTowardsPosition(EXAMPLE_IDEAL_POSITION_WITH_OLD_ENGINES);

        assertEquals(expectedTotalFuel, actualTotalFuel);
    }

    @Test
    void shouldSolvePuzzleTwo() {
        final var expectedIdealPosition = 464;
        final var expectedTotalFuel = 105461913;
        fleet.spawnSubmarines(puzzleInput, EngineType.OLD);

        final var actualIdealPosition = fleet.findIdealPosition();
        final var actualTotalFuel = fleet.calculateTotalFuelSpentTowardsPosition(actualIdealPosition);

        assertEquals(expectedIdealPosition, actualIdealPosition);
        assertEquals(expectedTotalFuel, actualTotalFuel);
        System.out.println("Day seven part two solution: " + actualTotalFuel);
    }

}
