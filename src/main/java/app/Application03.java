package app;

import aco.AntColonyParameterOptimizer;
import aco.AntColonyParameters;
import aco.OptimisationResult;
import data.DataInstance;
import data.DataReader;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Application03 {
    public static void main(String[] args) {
        // TODO: Application03
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);

        Configuration config = Configuration.INSTANCE;
        double[][] distanceMatrix = dataInstance.getDistanceMatrix();
        int numValuationIterations = 100;
        double[] alphas = new double[]{config.alpha};
        double[] betas = new double[]{config.beta};
        // Random factor pretty much changes nothing. All random generates better results...
        double[] randomFactors = new double[]{config.randomFactor};
        double[] initialPheromoneValues = new double[]{config.initialPheromoneValue};
        // The best one of this is sometimes higher, sometimes lower.
        double[] pheromoneEvaporations = new double[]{config.pheromoneEvaporation};
        // Best of this is sometimes higher, sometimes lower.
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
            optimisationResults.forEach(res -> System.out.printf("%6g | "+res.parameters()+"\n", res.distance()));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Execution of parameter optimisation was not successful.");
        }
    }

    private static double[] produceVariations(double originalValue) {
        return new double[]{originalValue, originalValue/2, originalValue/5, originalValue/10, originalValue*2, originalValue*5, originalValue*10};
    }
}
