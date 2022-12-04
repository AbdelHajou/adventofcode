package nl.abdel.aoc;

public class Submarine {

    protected int position;
    protected int depth;

    public Submarine() {
        this(0, 0);
    }

    public Submarine(final int initialPosition, final int initialDepth) {
        this.position = initialPosition;
        this.depth = initialDepth;
    }

    public int getPosition() {
        return position;
    }

    public int getDepth() {
        return depth;
    }

    public void command(final String command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null.");
        }

        final String[] commandParts = command.split(" ");
        final String direction = commandParts[0];
        final int units = Integer.parseInt(commandParts[1]);

        switch (direction) {
            case "forward" -> forward(units);
            case "down" -> down(units);
            case "up" -> up(units);
            default -> {
            }
        }
    }

    protected void forward(final int units) {
        position += units;
    }

    protected void down(final int units) {
        depth += units;
    }

    protected void up(final int units) {
        depth -= units;
    }
}
