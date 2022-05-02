package aco;

public class Ant {
    private final int trailSize;
    private final int[] trail;
    private double trailLength;

    public Ant(int trailLength) {
        this.trailSize = trailLength;
        this.trail = new int[this.trailSize];
    }

    public int[] getTrail() {
        return trail;
    }

    public double getTrailLength() {
        return trailLength;
    }
}
