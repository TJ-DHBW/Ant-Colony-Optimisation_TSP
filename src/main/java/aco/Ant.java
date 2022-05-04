package aco;

import java.util.Arrays;
import java.util.Random;

public class Ant {
    private final AntParameters parameters;
    private final AntColony antColony;
    private final Random randomGenerator;

    private final int[] trail;
    private double trailDistance;
    private final boolean[] visited;

    public Ant(AntParameters parameters, AntColony antColony, Random randomGenerator) {
        this.parameters = parameters;
        this.antColony = antColony;
        this.randomGenerator = randomGenerator;

        int trailSize = this.antColony.getNumCities();
        this.trail = new int[trailSize];
        this.visited = new boolean[trailSize];

        this.chooseStartingCity();
    }

    private void chooseStartingCity() {
        int startingCity = this.randomGenerator.nextInt(this.trail.length);
        this.trail[0] = startingCity;
        this.visited[startingCity] = true;
    }

    public void findTrail() {
        this.resetTrail();

        for (int i = 1; i < trail.length; i++) {
            int nextCity = selectNextCityIndex(i);
            this.trail[i] = nextCity;
            visited[nextCity] = true;
        }

        this.trailDistance = this.calculateLength();
    }

    private int selectNextCityIndex(int trailIndex) {
        double nextCityRandomRoll = this.randomGenerator.nextDouble();
        if (nextCityRandomRoll < this.parameters.randomFactor()) {
            int randomIndex = this.randomGenerator.nextInt(this.trail.length - trailIndex);
            if (!this.visited[randomIndex]) {
                return randomIndex;
            }
        }

        double[] probabilities = this.calculateProbabilities(this.trail[trailIndex-1]);

        double randomNum = this.randomGenerator.nextDouble();
        double accumulatedProbability = 0.0;

        for (int i = 0; i < probabilities.length; i++) {
            accumulatedProbability += probabilities[i];
            if (accumulatedProbability > randomNum) {
                if (this.visited[i]) throw new RuntimeException("Selected city #%d, but it has already been visited.".formatted(i));
                return i;
            }
        }

        throw new RuntimeException("Was not able to select a city!");
    }

    private double[] calculateProbabilities(int originCity) {
        double[] ret = new double[this.trail.length];
        double totalDesirability = 0.0;

        for (int i = 0; i < this.trail.length; i++) {
            if (!this.visited[i]) {
                totalDesirability += this.calculateEdgeDesirability(originCity, i);
            }
        }

        for (int i = 0; i < this.trail.length; i++) {
            if (this.visited[i]) {
                ret[i] = 0.0;
            } else {
                double numerator = this.calculateEdgeDesirability(originCity, i);
                ret[i] = numerator / totalDesirability;
            }
        }

        return ret;
    }

    private double calculateEdgeDesirability(int fromIndex, int toIndex) {
        double[][] distanceMatrix = this.antColony.getDistanceMatrix();
        double[][] pheromoneMatrix = this.antColony.getPheromoneTrails();
        return Math.pow(pheromoneMatrix[fromIndex][toIndex], this.parameters.alpha())
                * Math.pow(distanceMatrix[fromIndex][toIndex], this.parameters.beta());
    }

    private double calculateLength() {
        double lengthAccumulator = 0.0;

        int toIndex = this.trail[this.trail.length -1];
        for (int nextValue : this.trail) {
            int fromIndex = toIndex;
            toIndex = nextValue;
            lengthAccumulator += this.antColony.getDistanceMatrix()[fromIndex][toIndex];
        }

        return lengthAccumulator;
    }

    private void resetTrail() {
        for (int i = 1; i < this.trail.length; i++) {
            this.trail[i] = -1;
        }
        Arrays.fill(this.visited, false);
        this.visited[this.trail[0]] = true;
    }

    public int[] getTrail() {
        return trail;
    }

    public double getTrailDistance() {
        return trailDistance;
    }
}
