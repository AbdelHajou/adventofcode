package nl.abdel.aoc.twentyone;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class VentScanner {

    public int[][] scanLineSegments(final String lineSegments, final boolean includeDiagonalLines) {
        final String[] segments = lineSegments.split(System.lineSeparator());
        final List<Line> lines = parseLines(segments);

        final int diagramWidth = getDiagramWidth(lines);
        final int diagramHeight = getDiagramHeight(lines);

        final int[][] diagram = new int[diagramHeight][diagramWidth];
        for (final Line line : lines) {
            if (includeDiagonalLines || (line.x1 == line.x2 || line.y1 == line.y2)) {
                fillLine(diagram, line);
            }
        }

        return diagram;
    }

    private void fillLine(final int[][] diagram, final Line line) {
        int x = line.x1;
        int y = line.y1;
        while (x != line.x2 || y != line.y2) {
            diagram[y][x]++;
            x += Comparator.comparingInt(Integer::intValue).compare(line.x2, x);
            y += Comparator.comparingInt(Integer::intValue).compare(line.y2, y);
        }
        diagram[y][x]++;
    }

    private List<Line> parseLines(final String[] segments) {
        final List<Line> lines = new LinkedList<>();
        for (final String segment : segments) {
            final String[] points = segment.split(" -> ");
            final String[] point1 = points[0].split(",");
            final String[] point2 = points[1].split(",");
            lines.add(new Line(Integer.parseInt(point1[0]), Integer.parseInt(point1[1]), Integer.parseInt(point2[0]), Integer.parseInt(point2[1])));
        }
        return lines;
    }

    private int getDiagramWidth(final List<Line> lines) {
        return lines.stream().max(Comparator.comparingInt(Line::maxX)).get().maxX() + 1;
    }

    private int getDiagramHeight(final List<Line> lines) {
        return lines.stream().max(Comparator.comparingInt(Line::maxY)).get().maxY() + 1;
    }

    private record Line(int x1, int y1, int x2, int y2) {
        int maxX() {
            return Math.max(x1, x2);
        }

        int maxY() {
            return Math.max(y1, y2);
        }
    }
}
