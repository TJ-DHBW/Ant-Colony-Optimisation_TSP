package app;

import data.DataInstance;
import data.DataReader;
import utility.Permutations;

import java.util.ArrayList;

public class Application01 {
    public static void main(String[] args) {
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);

        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < dataInstance.getNumPositions(); i++) {
            indexList.add(i);
        }
        Permutations permutations = new Permutations(indexList);

        double bestDistance = dataInstance.calculateRouteDistance(indexList);
        ArrayList<Integer> bestRoute = new ArrayList<>(indexList);
        int i = 0;
        for (ArrayList<Integer> permutation : permutations) {
            i++;
            double permutationDistance = dataInstance.calculateRouteDistance(permutation);
            if (permutationDistance < bestDistance) {
                bestDistance = permutationDistance;
                bestRoute = permutation;
            }
            if (i >= Configuration.INSTANCE.maxIterations) break;
        }

        System.out.printf("Best distance: %.2f%n", bestDistance);
        System.out.println("With route: " + bestRoute);
    }
}
