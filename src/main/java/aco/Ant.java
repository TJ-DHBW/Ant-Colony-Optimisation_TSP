package aco;

import app.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        Configuration.INSTANCE.logger.info(this + " found new trail(" + this.trailDistance + "): " + Arrays.toString(this.trail));
    }

    private int selectNextCityIndex(int trailIndex) {
        double nextCityRandomRoll = this.randomGenerator.nextDouble();
        if (nextCityRandomRoll < this.parameters.randomFactor()) {
            int randomIndex = this.randomGenerator.nextInt(this.trail.length - trailIndex);
            if (!this.visited[randomIndex]) {
                return randomIndex;
            }
        }

        int originCity = this.trail[trailIndex - 1];
        ArrayList<Double> edgeDesirabilities = new ArrayList<>();
        ArrayList<Integer> cityIndexs = new ArrayList<>();
        for (int i = 0; i < this.trail.length; i++) {
            if (!this.visited[i] && i != originCity) {
                edgeDesirabilities.add(this.calculateEdgeDesirability(originCity, i));
                cityIndexs.add(i);
            }
        }

        double totalDesirability = edgeDesirabilities.stream().reduce(0.0, Double::sum);
        List<Double> probabilities = edgeDesirabilities.stream().map(desirability -> desirability / totalDesirability).toList();

        double nextProbability = this.randomGenerator.nextDouble();
        double probabilitySum = 0.0;
        int currentIndex = 0;
        while (currentIndex < cityIndexs.size() && probabilitySum + probabilities.get(currentIndex) < nextProbability) {
            probabilitySum += probabilities.get(currentIndex);
            currentIndex++;
        }

        return cityIndexs.get(currentIndex);
    }

    private double calculateEdgeDesirability(int fromIndex, int toIndex) {
        double[][] distanceMatrix = this.antColony.getDistanceMatrix();
        double[][] pheromoneMatrix = this.antColony.getPheromoneTrails();
        return Math.pow(pheromoneMatrix[fromIndex][toIndex], this.parameters.alpha())
                * Math.pow(1 / distanceMatrix[fromIndex][toIndex], this.parameters.beta());
    }

    private double calculateLength() {
        double lengthAccumulator = 0.0;

        int toIndex = this.trail[this.trail.length - 1];
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
