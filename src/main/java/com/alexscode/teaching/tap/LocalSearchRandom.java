package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalSearchRandom implements TAPSolver {

    private static final int MAX_ITERATIONS = 2000;

    @Override
    public List<Integer> solve(Instance ist) {
        Objectives obj = new Objectives(ist);
        List<Integer> bestSolution = generateRandomSolution(ist);

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            List<Integer> neighborSolution = exploreNeighborhood(bestSolution, ist);

            double neighborLength = obj.time(neighborSolution);

            if (neighborLength < obj.time(bestSolution) && 
                obj.distance(neighborSolution) <= ist.getMaxDistance() && 
                obj.time(neighborSolution) <= ist.getTimeBudget()) {
                bestSolution = new ArrayList<>(neighborSolution);
            }
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

            if (obj.distance(randomSolution) > ist.getMaxDistance() || obj.time(randomSolution) > ist.getTimeBudget()) {
                // Remove the last query if constraints are violated
                randomSolution.remove(randomSolution.size() - 1);
                break;
            }
        }

        return randomSolution;
    }

    private List<Integer> exploreNeighborhood(List<Integer> solution, Instance ist) {
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
}