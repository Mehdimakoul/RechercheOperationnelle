package com.alexscode.teaching.tap;
import com.alexscode.teaching.tap.Instance;
import com.alexscode.teaching.tap.Objectives;
import com.alexscode.teaching.tap.TAPSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalSearch implements TAPSolver {

    // Définition de la constante pour le nombre maximal d'itérations
    private static final int MAX_ITERATIONS = 1000;

    @Override
    public List<Integer> solve(Instance ist) {
        // Initialisation des objectifs et des solutions
        Objectives obj = new Objectives(ist);
        List<Integer> currentSolution = new ArrayList<>(new TestHSimple().solve(ist));
        List<Integer> bestSolution = new ArrayList<>(currentSolution);

        // Boucle principale de l'algorithme
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            // Exploration du voisinage
            List<Integer> neighborSolution = exploreNeighborhood(currentSolution);

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
        }

        // Retourne la meilleure solution trouvée
        return bestSolution;
    }

    // Exploration du voisinage par échange de positions
    private List<Integer> exploreNeighborhood(List<Integer> solution) {
        List<Integer> neighborSolution = new ArrayList<>(solution);

        // Échange de deux positions dans la solution (voisinage par échange)
        int index1 = (int) (Math.random() * solution.size());
        int index2 = (int) (Math.random() * solution.size());

        Collections.swap(neighborSolution, index1, index2);

        return neighborSolution;
    }
}