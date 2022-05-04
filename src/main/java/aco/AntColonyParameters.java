package aco;

public record AntColonyParameters(double[][] distanceMatrix,
                                  AntParameters antParameters,
                                  int maxIterations,
                                  double initialPheromoneValue,
                                  double pheromoneEvaporation,
                                  double q,
                                  double antFactor) {
}
