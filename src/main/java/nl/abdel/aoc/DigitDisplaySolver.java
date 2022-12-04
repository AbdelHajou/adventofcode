package nl.abdel.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigitDisplaySolver {

    private static final int[] NUMBER_OF_SEGMENTS_PER_DIGIT = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};

    public String solveNotes(final String notes) {
        final Stream<String> noteEntries = notes.lines();
        final Stream<String> outputValues = noteEntries.map(this::solveNoteEntry);

        return outputValues.collect(Collectors.joining(" "));
    }

    private String solveNoteEntry(final String entry) {
        final String[] signalPatterns = entry.split("\\|")[0].split(" ");
        final String[] outputPatterns = entry.split("\\|")[1].split(" ");

        final Map<String, List<Integer>> possibleDigitsPerPattern = findPossibleDigitsPerPattern(signalPatterns);
        return findOutputValue(outputPatterns, possibleDigitsPerPattern);
    }

    private String findOutputValue(final String[] outputPatterns, final Map<String, List<Integer>> possibleDigitsPerPattern) {
        final StringBuilder outputValue = new StringBuilder();

        for (final String outputPattern : outputPatterns) {
            for (final String possiblePattern : possibleDigitsPerPattern.keySet()) {
                if (patternsAreEqual(outputPattern, possiblePattern)) {
                    final List<Integer> possibleDigits = possibleDigitsPerPattern.get(possiblePattern);
                    outputValue.append(possibleDigits.get(0));
                }
            }
        }

        return outputValue.toString();
    }

    private Map<String, List<Integer>> findPossibleDigitsPerPattern(final String[] signalPatterns) {
        final Map<String, List<Integer>> possibleDigitsPerPattern = new HashMap<>();
        for (final String signalPattern : signalPatterns) {
            for (int digit = 0; digit < NUMBER_OF_SEGMENTS_PER_DIGIT.length; digit++) {
                if (signalPattern.length() == NUMBER_OF_SEGMENTS_PER_DIGIT[digit]) {
                    if (!possibleDigitsPerPattern.containsKey(signalPattern)) {
                        possibleDigitsPerPattern.put(signalPattern, new ArrayList<>());
                    }
                    possibleDigitsPerPattern.get(signalPattern).add(digit);
                }
            }
        }
        return possibleDigitsPerPattern;
    }

    private static boolean patternsAreEqual(final String a, final String b) {
        if (a.length() != b.length()) {
            return false;
        }

        for (final String character : a.split("")) {
            if (!b.contains(character)) {
                return false;
            }
        }

        return true;
    }
}
