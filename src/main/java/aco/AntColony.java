package aco;

import app.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AntColony {
    private final AntColonyParameters parameters;
    private final double[][] distanceMatrix;
    private final double[][] pheromoneTrails;
    private final Ant[] ants;
    private final Random randomGenerator;

    private int[] bestTourOrder;
    private double bestTourLength;


    public AntColony(AntColonyParameters parameters, double[][] distanceMatrix, Random randomGenerator) {
        this.parameters = parameters;

        this.distanceMatrix = distanceMatrix;
        this.validateDistanceMatrix();

        int numCities = this.getNumCities();
        this.pheromoneTrails = new double[numCities][numCities];

        // Probabilities are not needed as a field. It should be passed as local variables.

        this.randomGenerator = randomGenerator;
        int numAnts = (int) (numCities * this.parameters.antFactor());
        this.ants = new Ant[numAnts];
    }

    private void validateDistanceMatrix() {
        int numCities = this.getNumCities();
        for (double[] arr : this.distanceMatrix) {
            if (arr.length != numCities) throw new IllegalStateException("The distance matrix has to be square. At least one array was length %d, where it should have been %d.".formatted(arr.length, numCities));
        }
    }

    private void createAnts() {
        for (int i = 0; i < this.ants.length; i++) {
            this.ants[i] = new Ant(this.parameters.antParameters(), this, this.randomGenerator);
        }
    }

    private void initPheromoneTrails(double initialValue) {
        for (double[] pheromoneTrail : this.pheromoneTrails) {
            Arrays.fill(pheromoneTrail, initialValue);
        }
    }

    public void run() {
        // create and clear Ants
        this.createAnts();
        // init trails with given number
        this.initPheromoneTrails(this.parameters.initialPheromoneValue());


        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < this.parameters.maxIterations(); i++) {
            // TODO: Remove logging
            System.out.print(".");
            // let the ants generate a path.
            if(Configuration.INSTANCE.useThreads) {
                this.moveAnts(executorService);
            }else {
                this.moveAnts();
            }
            // evaporate pheromones and distribute pheromones from ants.
            this.updateTrails();
            // update best tour and best distance
            this.updateBest();
        }
        executorService.shutdown();
    }

    private void moveAnts(ExecutorService executorService) {
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (Ant ant : this.ants) {
            futures.add(executorService.submit(ant::findTrail));
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.out.println("The moving of the ants was not successful");
            }
        }
    }

    private void moveAnts() {
        for (Ant ant : this.ants) {
            ant.findTrail();
        }
    }

    private void updateTrails() {
        for (int i = 0; i < this.pheromoneTrails.length; i++) {
            for (int j = 0; j < this.pheromoneTrails[i].length; j++) {
                this.pheromoneTrails[i][j] = (1-this.parameters.pheromoneEvaporation()) * this.pheromoneTrails[i][j];
            }
        }

        // TODO: This could be parallelized
        for (Ant ant : this.ants) {
            double contribution = this.parameters.q() / ant.getTrailDistance();
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
            this.bestTourOrder = this.ants[0].getTrail().clone();
            this.bestTourLength = this.ants[0].getTrailDistance();
        }

        for (Ant ant : this.ants) {
            if (ant.getTrailDistance() < this.bestTourLength) {
                this.bestTourOrder = ant.getTrail().clone();
                this.bestTourLength = ant.getTrailDistance();
                // TODO: Remove logging
                System.out.println("New Best: "+this.bestTourLength);
            }
        }
    }

    public double[][] getPheromoneTrails() {
        return pheromoneTrails;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getNumCities() {
        return distanceMatrix.length;
    }

    public int[] getBestTourOrder() {
        return bestTourOrder;
    }

    public double getBestTourLength() {
        return bestTourLength;
    }
}
