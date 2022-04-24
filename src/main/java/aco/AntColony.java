package aco;

import data.DataInstance;

import java.util.ArrayList;
import java.util.Arrays;

public class AntColony {
    private final double[][] distanceMatrix;
    private final double[][] pheromoneTrails;
    private final ArrayList<Ant> ants;

    public AntColony(DataInstance dataInstance, double initialPheromoneLevel) {
        this.distanceMatrix = dataInstance.getDistanceMatrix();

        final int numPositions = dataInstance.getNumPositions();
        this.pheromoneTrails = new double[numPositions][numPositions];
        this.initPheromoneTrails(initialPheromoneLevel);

        // Probabilities are not needed as a field. It should be passed as local variables.

        this.ants = new ArrayList<>(numPositions);
        this.createAnts(numPositions);
    }

    private void createAnts(int numAnts) {
        for (int i = 0; i < numAnts; i++) {
            // TODO: Create proper Ants.
            this.ants.add(new Ant());
        }
    }

    private void initPheromoneTrails(double initialValue) {
        for (double[] pheromoneTrail : this.pheromoneTrails) {
            Arrays.fill(pheromoneTrail, initialValue);
        }
    }

    public void run(int numIterations) {
        // TODO: antColony loop
    }
}
