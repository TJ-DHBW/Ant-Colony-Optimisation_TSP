package app;

import utility.Distance;
import utility.DistanceFunction;

import java.util.Random;

public enum Configuration {
    INSTANCE;

    public final String dataName = "a280.txt";
    public final boolean useThreads = true;

    public final Random randomGenerator = new Random();

    public final int maxIterations = 1000;
    public final double alpha = 2;
    public final double beta = 20;
    public final double randomFactor = 0.002;
    public final double initialPheromoneValue = 0.00001;
    public final double pheromoneEvaporation = 0.05;
    public final double q = 1;
    public final double antFactor = 0.8;

    public final DistanceFunction distanceFunction = Distance::euclidean;
}
