package com.alexscode.teaching.tap;
//@author:ismail

import java.util.ArrayList;
import java.util.List;

public class TestInterestDistance implements TAPSolver {

    @Override
    public List<Integer> solve(Instance ist) {

        List<Integer> demo = new ArrayList<>();
        Objectives obj = new Objectives(ist);

        int q_idx = 0;

        while (obj.distance(demo) <= ist.getMaxDistance() && obj.time(demo) <= ist.getTimeBudget()) {
            double maxRatio = 0;

            // Parcourt toutes les requêtes pour trouver celle avec le meilleur ratio
            for (int i = 0; i < ist.getSize(); i++) {
                // Si la requête n'est pas déjà dans la solution
                if (!demo.contains(i)) {
                    // Calcule le ratio basé sur une combinaison de l'intérêt et la distance
                    double ratio = ist.getInterest()[i] * (1.0 / ist.getDistances()[i][q_idx]);

                    // Si le ratio est meilleur que le meilleur ratio actuel
                    if (ratio > maxRatio) {
                        // Met à jour le meilleur ratio et l'index de la requête
                        maxRatio = ratio;
                        q_idx = i;
                    }
                }
            }

            // Ajoute la requête avec le meilleur ratio à la solution
            demo.add(q_idx);
        }

        // Supprime la dernière requête ajoutée car elle dépasse probablement les contraintes de temps et de distance
        demo.remove(demo.size() - 1);

        // Retourne la liste des indices des requêtes sélectionnées comme solution
        return demo;
    }
}

