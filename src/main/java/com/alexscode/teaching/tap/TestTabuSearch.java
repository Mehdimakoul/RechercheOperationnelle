package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestTabuSearch implements TAPSolver {
    private static final int MAX_ITERATIONS = 1000; // nombre itétation maximale
    private static final int TABU_TENURE = 10;

    @Override
    public List<Integer> solve(Instance ist) {


        Objectives obj = new Objectives(ist);
        List<Integer> currentSolution = new ArrayList<>(new BestQuery().solve(ist));
        List<Integer> bestSolution = new ArrayList<>(currentSolution);
        List<Integer> tabuList = new ArrayList<>();

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {


            List<Integer> neighborSolution = exploreNeighborhood(currentSolution, tabuList);


            double currentLength = obj.time(currentSolution);
            double neighborLength = obj.time(neighborSolution);



            if (neighborLength < currentLength) {
                currentSolution = new ArrayList<>(neighborSolution);
            }
            if (obj.time(currentSolution) < obj.time(bestSolution)) {
                bestSolution = new ArrayList<>(currentSolution);
            }



            updateTabuList(tabuList);
        }
        return bestSolution;
    }


    private List<Integer> exploreNeighborhood(List<Integer> solution, List<Integer> tabuList) {
        List<Integer> neighborSolution = new ArrayList<>(solution);

        int index1 = (int) (Math.random() * solution.size());
        
        int index2 = (int) (Math.random() * solution.size());

        Collections.swap(neighborSolution, index1, index2);

        // Ajout mvt dans la liste tabou
        if (!isTabuMove(index1, index2, tabuList)) {
            tabuList.add(generateTabuKey(index1, index2));
        }

        return neighborSolution;
    }

    
    private void updateTabuList(List<Integer> tabuList) { // Mise à jour de la liste tabou en retirant le plus ancien 
        if (tabuList.size() >= TABU_TENURE) {
            tabuList.remove(0);
        }
    }

    // Vérification si le mvt est dans la liste tabou
    private boolean isTabuMove(int index1, int index2, List<Integer> tabuList) {
        int tabuKey = generateTabuKey(index1, index2);
        return tabuList.contains(tabuKey);
    }

    // Génération clé unique pour représenter un mvt
    private int generateTabuKey(int index1, int index2) {
        
        return index1 * 100 + index2;
    }
}