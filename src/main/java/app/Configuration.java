package app;

import utility.ACOLogger;
import utility.Distance;
import utility.DistanceFunction;

import java.util.Random;
import java.util.logging.Logger;

public enum Configuration {
    INSTANCE;

    public final String dataName = "a280.txt";
    public final String parameterFilePath = "parameters.json";
    public final String logFilePath = "log.txt";
    public final boolean useThreads = true;
    public final boolean writeParametersToFile = true;
    public final boolean writeToStdOut = false;

    public final Logger logger = ACOLogger.createLogger("ACOLogger", this.logFilePath);
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
