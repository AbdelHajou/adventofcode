package nl.abdel.aoc.twentyone;

import nl.abdel.aoc.twentyone.LanternFishSpawner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(2021_6)
class LanternFishSpawnerTest {

    private LanternFishSpawner spawner;

    private static final String EXAMPLE_INITIAL_STATE = "3,4,3,1,2";

    private static final String PUZZLE_INPUT = "5,3,2,2,1,1,4,1,5,5,1,3,1,5,1,2,1,4,1,2,1,2,1,4,2,4,1,5,1,3,5,4,3,3,1,4,1,3,4,4,1,5,4,3,3,2,5,1,1,3,1,4,3,2,2,3,1,3,1,3,1,5,3,5,1,3,1,4,2,1,4,1,5,5,5,2,4,2,1,4,1,3,5,5,1,4,1,1,4,2,2,1,3,1,1,1,1,3,4,1,4,1,1,1,4,4,4,1,3,1,3,4,1,4,1,2,2,2,5,4,1,3,1,2,1,4,1,4,5,2,4,5,4,1,2,1,4,2,2,2,1,3,5,2,5,1,1,4,5,4,3,2,4,1,5,2,2,5,1,4,1,5,1,3,5,1,2,1,1,1,5,4,4,5,1,1,1,4,1,3,3,5,5,1,5,2,1,1,3,1,1,3,2,3,4,4,1,5,5,3,2,1,1,1,4,3,1,3,3,1,1,2,2,1,2,2,2,1,1,5,1,2,2,5,2,4,1,1,2,4,1,2,3,4,1,2,1,2,4,2,1,1,5,3,1,4,4,4,1,5,2,3,4,4,1,5,1,2,2,4,1,1,2,1,1,1,1,5,1,3,3,1,1,1,1,4,1,2,2,5,1,2,1,3,4,1,3,4,3,3,1,1,5,5,5,2,4,3,1,4";

    @BeforeEach
    void setUp() {
        spawner = new LanternFishSpawner();
    }

    @Test
    void shouldTickInternalTimer() {
        final var expectedDaysUntilNewLanternFish = 2;

        spawner.spawn("3");
        spawner.passDay();

        assertEquals(0, spawner.getFishPerDaysLeft(3));
        assertEquals(1, spawner.getFishPerDaysLeft(expectedDaysUntilNewLanternFish));
    }

    @Test
    void shouldResetTimerAndCreateNewFish() {
        final var expectedInitialTimerOfChild = 8;
        final var expectedNewTimerOfParent = 6;

        spawner.spawn("0");
        spawner.passDay();

        assertEquals(0, spawner.getFishPerDaysLeft(0));
        assertEquals(1, spawner.getFishPerDaysLeft(expectedInitialTimerOfChild));
        assertEquals(1, spawner.getFishPerDaysLeft(expectedNewTimerOfParent));
    }

    // 3,4,3,1,2
    @Test
    void shouldSpawnLanternFishFromInitialState() {
        final var expectedTimers = new int[]{3,4,3,1,2};

        spawner.spawn(EXAMPLE_INITIAL_STATE);

        assertTimers(expectedTimers);
    }

    @Test
    void shouldSolveExample() {
        final var expectedNumberOfFishAfter18Days = 26;
        final var expectedNumberOfFishAfter80Days = 5934;

        spawner.spawn(EXAMPLE_INITIAL_STATE);

        spawner.passDays(18);
        assertEquals(expectedNumberOfFishAfter18Days, spawner.getNumberOfFishSpawned());

        spawner.passDays(62);
        assertEquals(expectedNumberOfFishAfter80Days, spawner.getNumberOfFishSpawned());
    }

    @Test
    void shouldSolvePuzzleOne() {
        final var expectedNumberOfFish = 359999;
        final var spawner = new LanternFishSpawner();

        spawner.spawn(PUZZLE_INPUT);
        spawner.passDays(80);

        assertEquals(expectedNumberOfFish, spawner.getNumberOfFishSpawned());
        System.out.println("Day six part one solution: " + expectedNumberOfFish);
    }

    @Test
    void shouldSolvePuzzleTwo() {
        final var expectedNumberOfFish = 1631647919273L;
        final var spawner = new LanternFishSpawner();

        spawner.spawn(PUZZLE_INPUT);
        spawner.passDays(256);

        assertEquals(expectedNumberOfFish, spawner.getNumberOfFishSpawned());
        System.out.println("Day six part two solution: " + expectedNumberOfFish);
    }

    @Test
    void shouldReturnZeroFishForHighNumberOfDaysLeft() {
        assertEquals(0, spawner.getFishPerDaysLeft(9));
        assertEquals(0, spawner.getFishPerDaysLeft(1000));
    }

    private void assertTimers(final int[] expectedTimes) {
        final long[] fishPerDaysLeft = new long[9];
        for (final int expectedTime : expectedTimes) {
            fishPerDaysLeft[expectedTime]++;
        }

        for (int i = 0; i < fishPerDaysLeft.length; i++) {
            assertEquals(fishPerDaysLeft[i], spawner.getFishPerDaysLeft(i));
        }
    }
}
