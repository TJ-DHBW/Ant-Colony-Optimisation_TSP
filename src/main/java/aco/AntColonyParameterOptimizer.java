package aco;

import app.Configuration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AntColonyParameterOptimizer {
    private final double[][] distanceMatrix;
    private final int numValuationIterations;
    private final double[] alphas;
    private final double[] betas;
    private final double[] randomFactors;
    private final double[] initialPheromoneValues;
    private final double[] pheromoneEvaporations;
    private final double[] qs;
    private final double[] antFactors;

    public AntColonyParameterOptimizer(double[][] distanceMatrix, int numValuationIterations, double[] alphas, double[] betas, double[] randomFactors, double[] initialPheromoneValues, double[] pheromoneEvaporations, double[] qs, double[] antFactors) {
        this.distanceMatrix = distanceMatrix;
        this.numValuationIterations = numValuationIterations;

        Configuration config = Configuration.INSTANCE;
        this.alphas = alphas != null ? alphas : new double[]{config.alpha};
        this.betas = betas != null ? betas : new double[]{config.beta};
        this.randomFactors = randomFactors != null ? randomFactors : new double[]{config.randomFactor};
        this.initialPheromoneValues = initialPheromoneValues != null ? initialPheromoneValues : new double[]{config.initialPheromoneValue};
        this.pheromoneEvaporations = pheromoneEvaporations != null ? pheromoneEvaporations : new double[]{config.pheromoneEvaporation};
        this.qs = qs != null ? qs : new double[]{config.q};
        this.antFactors = antFactors != null ? antFactors : new double[]{config.antFactor};
    }

    public ArrayList<OptimisationResult> optimize() throws ExecutionException, InterruptedException {
        ArrayList<AntColonyParameters> optimisationParameterss = new ArrayList<>();

        for (double alpha : alphas) {
            for (double beta : betas) {
                for (double randomFactor : randomFactors) {
                    AntParameters antParameters = new AntParameters(alpha, beta, randomFactor);
                    for (double initialPheromoneValue : initialPheromoneValues) {
                        for (double pheromoneEvaporation : pheromoneEvaporations) {
                            for (double q : qs) {
                                for (double antFactor : antFactors) {
                                    AntColonyParameters antColonyParameters = new AntColonyParameters(antParameters,
                                            numValuationIterations,
                                            initialPheromoneValue,
                                            pheromoneEvaporation,
                                            q,
                                            antFactor);
                                    optimisationParameterss.add(antColonyParameters);
                                }
                            }
                        }
                    }
                }
            }
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<Double>> antColonyFutures = new ArrayList<>();
        for (AntColonyParameters optimisationParameters : optimisationParameterss) {
            antColonyFutures.add(executorService.submit(() -> {
                AntColony antColony = new AntColony(optimisationParameters, distanceMatrix, Configuration.INSTANCE.randomGenerator);
                antColony.run();
                return antColony.getBestTourLength();
            }));
        }

        ArrayList<OptimisationResult> results = new ArrayList<>(antColonyFutures.size());
        for (int i = 0; i < antColonyFutures.size(); i++) {
            results.add(new OptimisationResult(antColonyFutures.get(i).get(), optimisationParameterss.get(i)));
        }
        executorService.shutdown();
        results.sort(Comparator.comparingDouble(OptimisationResult::distance));

        return results;
    }
}
