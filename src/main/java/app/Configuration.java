package app;

import utility.Distance;
import utility.DistanceFunction;

public enum Configuration {
    INSTANCE;

    public final String dataName = "a280.txt";

    public final int maxIterations = 10000;

    public final DistanceFunction distanceFunction = Distance::euclidean;
}
