package app;

import aco.AntColonyParameterOptimizer;
import aco.AntColonyParameters;
import aco.OptimisationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.DataInstance;
import data.DataReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Application03 {
    public static void main(String[] args) {
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);

        // These values can be changed to find optimal parameters.
        // Optimizing many parameters at once should be avoided, because of runtime.
        // That is also the reason why the user has to make educated guesses, instead of the application trying random numbers.
        Configuration config = Configuration.INSTANCE;
        double[][] distanceMatrix = dataInstance.getDistanceMatrix();
        int numValuationIterations = 50;
        double[] alphas = new double[]{config.alpha};
        //double[] betas = new double[]{config.beta};
        double[] betas = produceVariations(config.beta);
        double[] randomFactors = new double[]{config.randomFactor};
        double[] initialPheromoneValues = new double[]{config.initialPheromoneValue};
        double[] pheromoneEvaporations = new double[]{config.pheromoneEvaporation};
        double[] qs = new double[]{config.q};
        double[] antFactors = new double[]{config.antFactor};


        AntColonyParameterOptimizer parameterOptimizer = new AntColonyParameterOptimizer(distanceMatrix,
                numValuationIterations,
                alphas,
                betas,
                randomFactors,
                initialPheromoneValues,
                pheromoneEvaporations,
                qs,
                antFactors);

        try {
            ArrayList<OptimisationResult> optimisationResults = parameterOptimizer.optimize();
            System.out.println();
            optimisationResults.forEach(res -> System.out.printf("%6g | " + res.parameters() + "\n", res.distance()));

            if (config.writeParametersToFile) {
                ObjectMapper mapper = new ObjectMapper();
                AntColonyParameters bestParams = optimisationResults.get(0).parameters();
                AntColonyParameters bestParamsFixedIterations = new AntColonyParameters(bestParams.antParameters(),
                        config.maxIterations,
                        bestParams.initialPheromoneValue(),
                        bestParams.pheromoneEvaporation(),
                        bestParams.q(),
                        bestParams.antFactor());
                mapper.writeValue(new File(config.parameterFilePath), bestParamsFixedIterations);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Execution of parameter optimisation was not successful.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Was not able to write the parameters to a json file.");
        }
    }

    private static double[] produceVariations(double originalValue) {
        return new double[]{originalValue, originalValue / 2, originalValue / 5, originalValue / 10, originalValue * 2, originalValue * 5, originalValue * 10};
    }
}
