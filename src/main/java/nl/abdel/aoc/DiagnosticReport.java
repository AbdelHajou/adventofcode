package nl.abdel.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiagnosticReport {

    private final int gammaRate;
    private final int epsilonRate;
    private final int powerConsumption;
    private final int oxygenGeneratorRating;
    private final int co2ScrubberRating;
    private final int lifeSupportRating;
    
    private static final char ZERO_BIT = '0';
    private static final char ONE_BIT = '1';

    public DiagnosticReport(final String report) {
        final List<String> binaryNumbers = decodeReport(report);
        final List<BitOccurences> bitOccurences = calculateBitOccurrences(binaryNumbers);
        this.gammaRate = calculateGammaRate(bitOccurences);
        this.epsilonRate = calculateEpsilonRate(bitOccurences);
        this.powerConsumption = calculatePowerConsumption();
        this.oxygenGeneratorRating = calculateOxygenGeneratorRating(binaryNumbers);
        this.co2ScrubberRating = calculateCo2ScrubberRating(binaryNumbers);
        this.lifeSupportRating = calculateLifeSupportRating();
    }

    private List<String> decodeReport(final String report) {
        return Arrays.stream(report.split(System.lineSeparator())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<BitOccurences> calculateBitOccurrences(final List<String> binaryNumbers) {
        final List<BitOccurences> bitOccurrences = new ArrayList<>();

        for (final String binaryNumber : binaryNumbers) {
            for (int i = 0; i < binaryNumber.length(); i++) {
                increaseBitOccurrencesAtIndex(bitOccurrences, binaryNumber.charAt(i), i);
            }
        }

        return bitOccurrences;
    }

    private void increaseBitOccurrencesAtIndex(final List<BitOccurences> bitOccurrences, final char bit, final int index) {
        final Optional<BitOccurences> existing = bitOccurrences.stream().filter(bitOccurrence -> bitOccurrence.index == index && bitOccurrence.bit == bit).findFirst();

        if (existing.isPresent()) {
            existing.get().occurrences++;
        } else {
            bitOccurrences.add(new BitOccurences(index, bit, 1));
        }
    }

    private int calculateGammaRate(final List<BitOccurences> bitOccurences) {
        final StringBuilder gammaRateBits = new StringBuilder();
        final Set<Integer> indices = bitOccurences.stream()
                .map(bitOccurence -> bitOccurence.index)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (final int index : indices) {
            final char mostCommonBit = findCommonBitAtIndex(bitOccurences, index, BitCriteria.MOST_COMMON);
            gammaRateBits.append(mostCommonBit);
        }

        return Integer.parseInt(gammaRateBits.toString(), 2);
    }

    private int calculateEpsilonRate(final List<BitOccurences> bitOccurences) {
        final StringBuilder epsilonRateBits = new StringBuilder();
        final Set<Integer> indices = bitOccurences.stream()
                .map(bitOccurence -> bitOccurence.index)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (final int index : indices) {
            final char leastCommonBit = findCommonBitAtIndex(bitOccurences, index, BitCriteria.LEAST_COMMON);
            epsilonRateBits.append(leastCommonBit);
        }

        return Integer.parseInt(epsilonRateBits.toString(), 2);
    }

    private int calculatePowerConsumption() {
        return gammaRate * epsilonRate;
    }

    private int calculateOxygenGeneratorRating(final List<String> binaryNumbers) {
        return calculateOxygenGeneratorRating(new LinkedList<>(binaryNumbers), 0);
    }

    private int calculateOxygenGeneratorRating(final List<String> binaryNumbers, final int index) {
        final char mostCommonBit = findCommonBitAtIndex(calculateBitOccurrences(binaryNumbers), index, BitCriteria.MOST_COMMON, ONE_BIT);

        final LinkedList<String> numbersMatchingCriteria = binaryNumbers.stream()
                .filter(binaryNumber -> binaryNumber.charAt(index) == mostCommonBit)
                .collect(Collectors.toCollection(LinkedList::new));

        if (numbersMatchingCriteria.size() == 1) {
            return Integer.parseInt(numbersMatchingCriteria.get(0), 2);
        }

        return calculateOxygenGeneratorRating(numbersMatchingCriteria, index + 1);
    }

    private int calculateCo2ScrubberRating(final List<String> binaryNumbers) {
        return calculateCo2ScrubberRating(new LinkedList<>(binaryNumbers), 0);
    }

    private int calculateCo2ScrubberRating(final List<String> binaryNumbers, final int index) {
        final char leastCommonBit = findCommonBitAtIndex(calculateBitOccurrences(binaryNumbers), index, BitCriteria.LEAST_COMMON, ZERO_BIT);

        final LinkedList<String> numbersMatchingCriteria = binaryNumbers.stream()
                .filter(binaryNumber -> binaryNumber.charAt(index) == leastCommonBit)
                .collect(Collectors.toCollection(LinkedList::new));

        if (numbersMatchingCriteria.size() == 1) {
            return Integer.parseInt(numbersMatchingCriteria.get(0), 2);
        }

        return calculateCo2ScrubberRating(numbersMatchingCriteria, index + 1);
    }

    private char findCommonBitAtIndex(final List<BitOccurences> bitOccurences, final int index, final BitCriteria bitCriteria, final char defaultIfEqual) {
        final long zeroBitOccurrences = bitOccurences.stream()
                .filter(bitOccurence -> bitOccurence.index == index && bitOccurence.bit == ZERO_BIT)
                .findFirst()
                .get()
                .occurrences;
        final long oneBitOccurrences = bitOccurences.stream()
                .filter(bitOccurence -> bitOccurence.index == index && bitOccurence.bit == ONE_BIT)
                .findFirst()
                .get()
                .occurrences;
        if (zeroBitOccurrences == oneBitOccurrences) {
            return defaultIfEqual;
        }

        return findCommonBitAtIndex(bitOccurences, index, bitCriteria);
    }

    private char findCommonBitAtIndex(final List<BitOccurences> bitOccurences, final int index, final BitCriteria bitCriteria) {
        final Stream<BitOccurences> bitsAtIndex =  bitOccurences.stream()
                .filter(bitOccurence -> bitOccurence.index == index);

        return switch (bitCriteria) {
            case MOST_COMMON -> bitsAtIndex.max(Comparator.comparing(bitOccurence ->  bitOccurence.occurrences)).get().bit;
            case LEAST_COMMON -> bitsAtIndex.min(Comparator.comparing(bitOccurence ->  bitOccurence.occurrences)).get().bit;
        };
    }

    private int calculateLifeSupportRating() {
        return oxygenGeneratorRating * co2ScrubberRating;
    }

    public int getGammaRate() {
        return gammaRate;
    }

    public int getEpsilonRate() {
        return epsilonRate;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public int getOxygenGeneratorRating() {
        return oxygenGeneratorRating;
    }

    public int getCo2ScrubberRating() {
        return co2ScrubberRating;
    }

    public int getLifeSupportRating() {
        return lifeSupportRating;
    }

    private static class BitOccurences {
        int index;
        char bit;
        int occurrences;

        public BitOccurences(final int index, final char bit, final int occurrences) {
            this.index = index;
            this.bit = bit;
            this.occurrences = occurrences;
        }
    }

    private enum BitCriteria {
        LEAST_COMMON,
        MOST_COMMON
    }
}
