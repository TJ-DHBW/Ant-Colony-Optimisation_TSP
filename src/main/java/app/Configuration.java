package app;

import utility.Distance;
import utility.DistanceFunction;

import java.util.Random;

public enum Configuration {
    INSTANCE;

    public final String dataName = "a280.txt";
    public final boolean useThreads = true;

    public final Random randomGenerator = new Random();

    //public final int maxIterations = 10000;
    public final int maxIterations = 1000;
    public final double alpha = 0.001;
    public final double beta = 0.02;
    public final double randomFactor = 0.01;
    public final double initialPheromoneValue = 1.0;
    public final double pheromoneEvaporation = 0.05;
    public final double q = 500;
    //public final double antFactor = 0.8;
    public final double antFactor = 0.8;

    public final DistanceFunction distanceFunction = Distance::euclidean;
}
