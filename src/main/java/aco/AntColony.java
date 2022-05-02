package aco;

import java.util.ArrayList;
import java.util.Arrays;

public class AntColony {
    private final AntColonyParameters parameters;
    private final double[][] distanceMatrix;
    private final double[][] pheromoneTrails;
    private final ArrayList<Ant> ants;

    private int[] bestTourOrder;
    private double bestTourLength;


    public AntColony(AntColonyParameters parameters) {
        this.parameters = parameters;

        this.distanceMatrix = this.parameters.dataInstance().getDistanceMatrix();

        int numPositions = this.parameters.dataInstance().getNumPositions();
        this.pheromoneTrails = new double[numPositions][numPositions];

        // Probabilities are not needed as a field. It should be passed as local variables.

        int numAnts = (int) (numPositions * this.parameters.antFactor());
        this.ants = new ArrayList<>(numAnts);
        this.createAnts(numAnts, numPositions);
    }

    private void createAnts(int numAnts, int trailLength) {
        for (int i = 0; i < numAnts; i++) {
            this.ants.add(new Ant(trailLength));
        }
    }

    private void initPheromoneTrails(double initialValue) {
        for (double[] pheromoneTrail : this.pheromoneTrails) {
            Arrays.fill(pheromoneTrail, initialValue);
        }
    }

    public void run(int numIterations) {
        // TODO: setupAnts() - clear Ants and give them a start city
        // clearTrails() - init trails with given number
        this.initPheromoneTrails(this.parameters.initialPheromoneValue());


        for (int i = 0; i < this.parameters.maxIterations(); i++) {
            // TODO: moveAnts() - let the ants generate a path.
            // evaporate pheromones and distribute pheromones from ants.
            this.updateTrails();
            // update best tour and best distance
            this.updateBest();
        }
    }

    private void updateTrails() {
        for (int i = 0; i < this.pheromoneTrails.length; i++) {
            for (int j = 0; j < this.pheromoneTrails[i].length; j++) {
                this.pheromoneTrails[i][j] *= this.parameters.pheromoneEvaporation();
            }
        }

        for (Ant ant : this.ants) {
            double contribution = this.parameters.q() / ant.getTrailLength();
            int[] trail = ant.getTrail();
            for (int i = 0; i < trail.length - 1; i++) {
                int from = trail[i];
                int to = trail[i+1];
                this.pheromoneTrails[from][to] += contribution;
            }
            this.pheromoneTrails[trail[trail.length-1]][trail[0]] += contribution;
        }
    }

    private void updateBest() {
        if (this.bestTourOrder == null) {
            this.bestTourOrder = this.ants.get(0).getTrail().clone();
            this.bestTourLength = this.ants.get(0).getTrailLength();
        }

        for (Ant ant : this.ants) {
            if (ant.getTrailLength() < this.bestTourLength) {
                this.bestTourOrder = ant.getTrail().clone();
                this.bestTourLength = ant.getTrailLength();
            }
        }
    }
}
