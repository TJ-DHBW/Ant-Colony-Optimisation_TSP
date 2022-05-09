package app;

import aco.AntColony;
import aco.AntColonyParameters;
import aco.AntParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.DataInstance;
import data.DataReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Application02 {
    public static void main(String[] args) {
        AntColonyParameters antColonyParameters;
        if (args.length == 0) {
            antColonyParameters = Application02.createAntColonyParametersFromDefaults();
        }else if (args.length == 2 && args[0].equals("-best")) {
            try {
                antColonyParameters = Application02.createAntColonyParametersFromPath(args[1]);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while reading the parameters json.");
                return;
            }
        }else {
            System.out.println("The only option is \"-best <parameterJsonPath>\".");
            return;
        }

        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);
        AntColony antColony = new AntColony(antColonyParameters, dataInstance.getDistanceMatrix(), Configuration.INSTANCE.randomGenerator);

        antColony.run();
        // TODO: Logging to a file

        System.out.println("Best tour is: " + Arrays.toString(antColony.getBestTourOrder()));
        System.out.println("With length: " + antColony.getBestTourLength());
    }


    private static AntColonyParameters createAntColonyParametersFromPath(String parametersJsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(parametersJsonPath), AntColonyParameters.class);
    }

    private static AntColonyParameters createAntColonyParametersFromDefaults() {
        Configuration config = Configuration.INSTANCE;
        AntParameters antParameters = new AntParameters(config.alpha, config.beta, config.randomFactor);
        return new AntColonyParameters(antParameters,
                config.maxIterations,
                config.initialPheromoneValue,
                config.pheromoneEvaporation,
                config.q,
                config.antFactor);
    }
}
