package data;

import app.Configuration;
import utility.DistanceFunction;

import java.util.List;

/**
 * Stores the positions given to it. Continuous indexing always starts at 0.
 */
public class DataInstance {
    private final int[] coordinates;
    private final int numPositions;

    private double[][] distanceMatrix;
    private final DistanceFunction distanceFunction = Configuration.INSTANCE.distanceFunction;

    public DataInstance(List<int[]> positions) {
        this.coordinates = new int[positions.size()*2];
        this.numPositions = positions.size();
        this.distanceMatrix = null;

        for (int i = 0; i < positions.size(); i++) {
            int[] position = positions.get(i);
            if (position.length < 2) throw new RuntimeException("Every position has to consist of two integers.");

            this.coordinates[i*2] = position[0];
            this.coordinates[i*2+1] = position[1];
        }
    }

    public double[][] getDistanceMatrix() {
        if (this.distanceMatrix == null) {
            this.distanceMatrix = new double[this.numPositions][this.numPositions];

            for (int i = 0; i < this.numPositions; i++) {
                int fromX = this.getX(i);
                int fromY = this.getY(i);
                for (int j = i; j < this.numPositions; j++) {
                    if (i == j) {
                        this.distanceMatrix[i][j] = 0.0;
                        continue;
                    }
                    int toX = this.getX(j);
                    int toY = this.getY(j);
                    double distance = this.distanceFunction.calculateDistance(fromX, fromY, toX, toY);
                    // Cities 170 and 171 have the same coordinates. we define the distance between them as a small value.
                    distance = distance != 0.0 ? distance : Double.MIN_VALUE*1000;
                    this.distanceMatrix[i][j] = distance;
                    this.distanceMatrix[j][i] = distance;
                }
            }
        }

        return this.distanceMatrix;
    }

    public int[] getPosition(int index) {
        if (index < 0) throw new IllegalArgumentException("Index can not be negative. Received: %d.".formatted(index));
        if (index >= numPositions) throw new IndexOutOfBoundsException("Index (%d) out of bounds for %d positions.".formatted(index, numPositions));

        int scaledIndex = index*2;
        return new int[]{
                this.coordinates[scaledIndex],
                this.coordinates[scaledIndex+1]
        };
    }

    public int getX(int index) {
        if (index < 0) throw new IllegalArgumentException("Index can not be negative. Received: %d.".formatted(index));
        if (index >= numPositions) throw new IndexOutOfBoundsException("Index (%d) out of bounds for %d positions.".formatted(index, numPositions));

        int scaledIndex = index*2;
        return this.coordinates[scaledIndex];
    }

    public int getY(int index) {
        if (index < 0) throw new IllegalArgumentException("Index can not be negative. Received: %d.".formatted(index));
        if (index >= numPositions) throw new IndexOutOfBoundsException("Index (%d) out of bounds for %d positions.".formatted(index, numPositions));

        int scaledIndex = index*2;
        return this.coordinates[scaledIndex+1];
    }

    public int getNumPositions() {
        return numPositions;
    }

    public double calculateRouteDistance(List<Integer> indexes) {
        if (indexes.size() < 2) throw new IllegalArgumentException("A route has to consist of at least 2 indexes.");
        double totalDistance = 0.0;

        for (int i = 0; i < indexes.size()-1; i++) {
            totalDistance += this.getDistanceMatrix()[indexes.get(i)][indexes.get(i+1)];
        }
        totalDistance += this.getDistanceMatrix()[indexes.get(indexes.size()-1)][indexes.get(0)];

        return totalDistance;
    }
}
