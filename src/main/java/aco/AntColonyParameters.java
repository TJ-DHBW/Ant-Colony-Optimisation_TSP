package aco;

import data.DataInstance;

public record AntColonyParameters(DataInstance dataInstance,
                                  int maxIterations,
                                  double initialPheromoneValue,
                                  double alpha,
                                  double beta,
                                  double pheromoneEvaporation,
                                  double q,
                                  double antFactor) {
}
