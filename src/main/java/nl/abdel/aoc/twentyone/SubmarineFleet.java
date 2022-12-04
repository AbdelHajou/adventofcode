package nl.abdel.aoc.twentyone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubmarineFleet {

    record CrabSubmarine(int position, EngineType engineType) {
        int calculateFuelTowardsPosition(final int targetPosition) {
            return switch (engineType) {
                case OLD -> increasingFuel(Math.abs(targetPosition - position));
                case IMPROVED -> Math.abs(targetPosition - position);
            };
        }

        private int increasingFuel(final int steps) {
            int totalFuel = 0;
            int fuelExpenditure = 1;
            for (int i = 0; i < steps; i++) {
                totalFuel += fuelExpenditure++;
            }
            return totalFuel;
        }
    }

    private final List<CrabSubmarine> submarines = new ArrayList<>();

    public void spawnSubmarines(final String horizontalPositions, final EngineType engineType) {
        submarines.addAll(
                Arrays.stream(horizontalPositions.split(","))
                        .map(Integer::parseInt)
                        .map(position -> new CrabSubmarine(position, engineType))
                        .toList()
        );
    }

    public Integer findIdealPosition() {
        final int maxSubmarinePosition = submarines.stream().mapToInt(crab -> crab.position).max().getAsInt();

        Integer cheapestPosition = null;
        int cheapestTotalFuel = 0;
        for (int pos = 0; pos <= maxSubmarinePosition; pos++) {
            final int totalFuelTowardsPosition = calculateTotalFuelSpentTowardsPosition(pos);
            if (cheapestPosition == null || totalFuelTowardsPosition < cheapestTotalFuel) {
                cheapestPosition = pos;
                cheapestTotalFuel = totalFuelTowardsPosition;
            }
        }
        return cheapestPosition;
    }

    public int calculateTotalFuelSpentTowardsPosition(final int targetPosition) {
        int totalFuel = 0;
        for (final CrabSubmarine crabSubmarine : submarines) {
            totalFuel += crabSubmarine.calculateFuelTowardsPosition(targetPosition);
        }
        return totalFuel;
    }
}
