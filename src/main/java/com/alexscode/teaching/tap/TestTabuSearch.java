package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestTabuSearch implements TAPSolver {

    // Définition des constantes
    private static final int MAX_ITERATIONS = 1000;
    private static final int TABU_TENURE = 10;

    @Override
    public List<Integer> solve(Instance ist) {
        // Initialisation des objectifs et des solutions
        Objectives obj = new Objectives(ist);
        List<Integer> currentSolution = new ArrayList<>(new BestQuery().solve(ist));
        List<Integer> bestSolution = new ArrayList<>(currentSolution);
        List<Integer> tabuList = new ArrayList<>();

        // Boucle principale de l'algorithme
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            // Exploration du voisinage
            List<Integer> neighborSolution = exploreNeighborhood(currentSolution, tabuList);

            // Calcul des longueurs des solutions
            double currentLength = obj.time(currentSolution);
            double neighborLength = obj.time(neighborSolution);

            // Mise à jour de la solution courante si la voisine est meilleure
            if (neighborLength < currentLength) {
                currentSolution = new ArrayList<>(neighborSolution);
            }

            // Mise à jour de la meilleure solution si la courante est meilleure
            if (obj.time(currentSolution) < obj.time(bestSolution)) {
                bestSolution = new ArrayList<>(currentSolution);
            }

            // Mise à jour de la liste tabou
            updateTabuList(tabuList);
        }

        // Retourne la meilleure solution trouvée
        return bestSolution;
    }

    // Exploration du voisinage par échange de positions
    private List<Integer> exploreNeighborhood(List<Integer> solution, List<Integer> tabuList) {
        List<Integer> neighborSolution = new ArrayList<>(solution);

        // Échange de deux positions dans la solution (voisinage par échange)
        int index1 = (int) (Math.random() * solution.size());
        int index2 = (int) (Math.random() * solution.size());

        Collections.swap(neighborSolution, index1, index2);

        // Ajout du mouvement dans la liste tabou
        if (!isTabuMove(index1, index2, tabuList)) {
            tabuList.add(generateTabuKey(index1, index2));
        }

        return neighborSolution;
    }

    // Mise à jour de la liste tabou en retirant le plus ancien élément
    private void updateTabuList(List<Integer> tabuList) {
        if (tabuList.size() >= TABU_TENURE) {
            tabuList.remove(0);
        }
    }

    // Vérification si un mouvement est dans la liste tabou
    private boolean isTabuMove(int index1, int index2, List<Integer> tabuList) {
        int tabuKey = generateTabuKey(index1, index2);
        return tabuList.contains(tabuKey);
    }

    // Génération d'une clé unique pour représenter un mouvement
    private int generateTabuKey(int index1, int index2) {
        // Simple façon de générer une clé unique pour les mouvements tabous
        return index1 * 1000 + index2;
    }
}