package nl.abdel.aoc;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class InputHelper {

    public static final String INPUTS_FOLDER = "/inputs/";

    public static int[] readLinesToIntegers(final String fileName) throws IOException {
        final List<String> lines = IOUtils.readLines(InputHelper.class.getResourceAsStream(INPUTS_FOLDER + fileName), Charset.defaultCharset());
        return lines.stream().mapToInt(Integer::parseInt).toArray();
    }

    public static List<String> readLines(final String fileName) throws IOException {
        final List<String> lines = IOUtils.readLines(InputHelper.class.getResourceAsStream(INPUTS_FOLDER + fileName), Charset.defaultCharset());
        return lines;
    }

    public static String readFileToString(final String fileName) throws IOException {
        return new String(InputHelper.class.getResourceAsStream(INPUTS_FOLDER + fileName).readAllBytes(), Charset.defaultCharset());
    }
}
