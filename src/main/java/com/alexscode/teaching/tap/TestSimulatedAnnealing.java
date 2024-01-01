package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSimulatedAnnealing implements TAPSolver {

    private static final double INITIAL_TEMPERATURE = 1000.0;
    private static final double COOLING_RATE = 0.95;

    @Override
    public List<Integer> solve(Instance ist) {
        Objectives obj = new Objectives(ist);
        List<Integer> currentSolution = generateRandomSolution(ist);
        List<Integer> bestSolution = new ArrayList<>(currentSolution);

        double temperature = INITIAL_TEMPERATURE;

        while (temperature > 1.0) {
            List<Integer> neighborSolution = exploreNeighborhood(currentSolution);

            double currentLength = obj.time(currentSolution);

            if (isValidSolution(neighborSolution, ist)) {
                double neighborLength = obj.time(neighborSolution);

                if (neighborLength < currentLength || Math.random() < acceptanceProbability(currentLength, neighborLength, temperature)) {
                    currentSolution = new ArrayList<>(neighborSolution);
                }
            }

            if (obj.time(currentSolution) < obj.time(bestSolution) && isValidSolution(currentSolution, ist)) {
                bestSolution = new ArrayList<>(currentSolution);
            }

            temperature *= COOLING_RATE;
        }

        return bestSolution;
    }

    private List<Integer> generateRandomSolution(Instance ist) {
        List<Integer> randomSolution = new ArrayList<>();

        List<Integer> allQueries = new ArrayList<>();
        for (int i = 0; i < ist.getSize(); i++) {
            allQueries.add(i);
        }

        // Shuffle the list to create a random solution
        Collections.shuffle(allQueries);

        Objectives obj = new Objectives(ist);

        // Add queries to the solution until constraints are violated
        for (int query : allQueries) {
            randomSolution.add(query);

            if (!isValidSolution(randomSolution, ist)) {
                // Remove the last query if constraints are violated
                randomSolution.remove(randomSolution.size() - 1);
                break;
            }
        }

        return randomSolution;
    }

    private boolean isValidSolution(List<Integer> solution, Instance ist) {
        Objectives obj = new Objectives(ist);
        return obj.distance(solution) <= ist.getMaxDistance() && obj.time(solution) <= ist.getTimeBudget();
    }

    private List<Integer> exploreNeighborhood(List<Integer> solution) {
        List<Integer> neighborSolution = new ArrayList<>(solution);

        // Improved neighborhood exploration
        int index1 = (int) (Math.random() * solution.size());
        int index2 = (int) (Math.random() * solution.size());

        // Ensure distinct indices for swap
        while (index1 == index2) {
            index2 = (int) (Math.random() * solution.size());
        }

        Collections.swap(neighborSolution, index1, index2);

        return neighborSolution;
    }

    private double acceptanceProbability(double currentLength, double neighborLength, double temperature) {
        double deltaLength = neighborLength - currentLength;

        if (deltaLength < 0) {
            return 1.0;
        }

        return Math.exp(-deltaLength / temperature);
    }
}