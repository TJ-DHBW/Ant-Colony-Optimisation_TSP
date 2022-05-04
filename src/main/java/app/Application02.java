package app;

import aco.AntColony;
import aco.AntColonyParameters;
import aco.AntParameters;
import data.DataInstance;
import data.DataReader;

import java.util.Arrays;

public class Application02 {
    public static void main(String[] args) {
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);
        Configuration config = Configuration.INSTANCE;

        AntParameters antParameters = new AntParameters(config.alpha, config.beta, config.randomFactor);
        AntColonyParameters antColonyParameters = new AntColonyParameters(dataInstance.getDistanceMatrix(),
                antParameters,
                config.maxIterations,
                config.initialPheromoneValue,
                config.pheromoneEvaporation,
                config.q,
                config.antFactor);
        AntColony antColony = new AntColony(antColonyParameters, Configuration.INSTANCE.randomGenerator);

        antColony.run();
        // TODO: Logging to a file

        System.out.println("Best tour is: " + Arrays.toString(antColony.getBestTourOrder()));
        System.out.println("With length: " + antColony.getBestTourLength());
    }
}
