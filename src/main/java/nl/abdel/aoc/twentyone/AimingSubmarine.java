package nl.abdel.aoc.twentyone;

public class AimingSubmarine extends Submarine {

    private int aim;

    public AimingSubmarine() {
        this(0, 0);
    }

    public AimingSubmarine(final int initialPosition, final int initialDepth) {
        this(initialPosition, initialDepth, 0);
    }

    public AimingSubmarine(final int initialPosition, final int initialDepth, final int initialAim) {
        super(initialPosition, initialDepth);
        this.aim = initialAim;
    }

    @Override
    protected void down(final int units) {
        aim += units;
    }

    @Override
    protected void up(final int units) {
        aim -= units;
    }

    @Override
    protected void forward(final int units) {
        position += units;
        depth += aim * units;
    }

    public int getAim() {
        return aim;
    }
}
