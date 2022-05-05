package aco;

public record AntColonyParameters(AntParameters antParameters,
                                  int maxIterations,
                                  double initialPheromoneValue,
                                  double pheromoneEvaporation,
                                  double q,
                                  double antFactor) {
}
