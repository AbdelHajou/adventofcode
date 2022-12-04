package nl.abdel.aoc.twentytwo;

import nl.abdel.aoc.InputHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Order(2022_1)
class FoodInventoryTest {

    private static String exampleCalorieList;

    private static String puzzleCalorieList;

    @BeforeAll
    static void readInputFiles() throws IOException {
        exampleCalorieList = InputHelper.readFileToString("2022/calorie_list_example.txt");
        puzzleCalorieList = InputHelper.readFileToString("2022/calorie_list.txt");
    }

    @Test
    void shouldSolveExampleInput() {
        final var expectedTotalCalories = 24_000;
        final var foodInventory = new FoodInventory(exampleCalorieList);

        final var actualTotalCalories = foodInventory.getHighestCalorieCount();

        assertThat(actualTotalCalories).isEqualTo(expectedTotalCalories);
    }

    @Test
    void shouldSolvePuzzleInput() {
        final var expectedTotalCalories = 70_296;
        final var foodInventory = new FoodInventory(puzzleCalorieList);

        final var actualTotalCalories = foodInventory.getHighestCalorieCount();

        assertThat(actualTotalCalories).isEqualTo(expectedTotalCalories);
    }
}
