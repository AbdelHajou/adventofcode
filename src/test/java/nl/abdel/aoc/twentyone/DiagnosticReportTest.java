package nl.abdel.aoc.twentyone;

import nl.abdel.aoc.InputHelper;
import nl.abdel.aoc.twentyone.DiagnosticReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(2021_3)
class DiagnosticReportTest {

    private static String exampleReport;

    private static String puzzleReport;

    @BeforeAll
    static void readInputFiles() throws IOException {
        exampleReport = InputHelper.readFileToString("2021/diagnostic_report_example.txt");
        puzzleReport = InputHelper.readFileToString("2021/diagnostic_report.txt");
    }

    @Test
    void shouldCalculateGammaRate() {
        final var expectedGammaRate = 22;

        final var diagnosticReport = new DiagnosticReport(exampleReport);

        assertEquals(expectedGammaRate, diagnosticReport.getGammaRate());
    }

    @Test
    void shouldCalculateEpsilon() {
        final var expectedEpsilon = 9;

        final var diagnosticReport = new DiagnosticReport(exampleReport);

        assertEquals(expectedEpsilon, diagnosticReport.getEpsilonRate());
    }

    @Test
    void shouldCalculatePowerConsumption() {
        final var expectedPowerConsumption = 198;

        final var diagnosticReport = new DiagnosticReport(exampleReport);

        assertEquals(expectedPowerConsumption, diagnosticReport.getPowerConsumption());
    }

    @Test
    void shouldSolvePuzzleOne() {
        final var expectedGamma = 349;
        final var expectedEpsilon = 3746;
        final var expectedPowerConsumption = 1307354;

        final var diagnosticReport = new DiagnosticReport(puzzleReport);

        assertEquals(expectedGamma, diagnosticReport.getGammaRate());
        assertEquals(expectedEpsilon, diagnosticReport.getEpsilonRate());
        assertEquals(expectedPowerConsumption, diagnosticReport.getPowerConsumption());
        System.out.println("Day three part one solution: " + diagnosticReport.getPowerConsumption());
    }

    @Test
    void shouldCalculateOxygenGeneratorRating() {
        final var expectedOxygenGeneratorRating = 23;

        final var diagnosticReport = new DiagnosticReport(exampleReport);

        assertEquals(expectedOxygenGeneratorRating, diagnosticReport.getOxygenGeneratorRating());
    }

    @Test
    void shouldCalculateCo2ScrubberRating() {
        final var expectedCo2ScrubberRating = 10;

        final var diagnosticReport = new DiagnosticReport(exampleReport);

        assertEquals(expectedCo2ScrubberRating, diagnosticReport.getCo2ScrubberRating());
    }

    @Test
    void shouldCalculateLifeSupportRating() {
        final var expectedLifeSupportRating = 230;

        final var diagnosticReport = new DiagnosticReport(exampleReport);

        assertEquals(expectedLifeSupportRating, diagnosticReport.getLifeSupportRating());
    }

    @Test
    void shouldSolvePuzzleTwo() {
        final var expectedOxygenGeneratorRating = 125;
        final var expectedCo2ScrubberRating = 3860;
        final var expectedLifeSupportRating = 482500;

        final var diagnosticReport = new DiagnosticReport(puzzleReport);

        assertEquals(expectedOxygenGeneratorRating, diagnosticReport.getOxygenGeneratorRating());
        assertEquals(expectedCo2ScrubberRating, diagnosticReport.getCo2ScrubberRating());
        assertEquals(expectedLifeSupportRating, diagnosticReport.getLifeSupportRating());
        System.out.println("Day three part two solution: " + diagnosticReport.getLifeSupportRating());
    }
}
