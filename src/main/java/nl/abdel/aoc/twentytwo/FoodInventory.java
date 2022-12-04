package nl.abdel.aoc.twentytwo;

import java.util.Arrays;
import java.util.List;

public class FoodInventory {

    public static final String BAG_SEPARATOR = System.lineSeparator() + System.lineSeparator();
    private final List<FoodBag> bagsOfFood;
    public FoodInventory(final String calorieList) {
        bagsOfFood = Arrays
                .stream(calorieList.split(BAG_SEPARATOR))
                .map(caloriesInBag -> caloriesInBag.split(System.lineSeparator()))
                .map(caloriesInBag -> Arrays.stream(caloriesInBag).mapToLong(Long::valueOf).sum())
                .map(FoodBag::new)
                .toList();

    }

    public long getHighestCalorieCount() {
        return bagsOfFood.stream()
                .mapToLong(FoodBag::totalCalories)
                .max()
                .orElse(0L);
    }

    record FoodBag(long totalCalories) {}
}
