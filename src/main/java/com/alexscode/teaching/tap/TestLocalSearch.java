package com.alexscode.teaching.tap;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestLocalSearch implements TAPSolver {

    // nombre maximal d'itérations
    private static final int MAX_ITERATIONS = 1000;

    @Override
    public List<Integer> solve(Instance ist) {

        Objectives obj = new Objectives(ist);
        List<Integer> currentSolution = new ArrayList<>(new BestQuery().solve(ist));
        List<Integer> bestSolution = new ArrayList<>(currentSolution);

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            List<Integer> neighborSolution = exploreNeighborhood(currentSolution);
            double currentLength = obj.time(currentSolution);
            double neighborLength = obj.time(neighborSolution);




            if (neighborLength < currentLength) {
                currentSolution = new ArrayList<>(neighborSolution);
            }
            if (obj.time(currentSolution) < obj.time(bestSolution)) {
                bestSolution = new ArrayList<>(currentSolution);
            }
        }
        return bestSolution;
    }
    // Explore du voisinage par échange de positions
    private List<Integer> exploreNeighborhood(List<Integer> solution) {
        List<Integer> neighborSolution = new ArrayList<>(solution);
        int index1 = (int) (Math.random() * solution.size());
        
        int index2 = (int) (Math.random() * solution.size());



        Collections.swap(neighborSolution, index1, index2);

        return neighborSolution;
    }
}