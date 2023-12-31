package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.List;

public class TestHSimple implements TAPSolver {
    @Override
    public List<Integer> solve(Instance ist) {
        List<Integer> solution = new ArrayList<>();
        Objectives obj = new Objectives(ist);

        // Ajouter la requête qui maximise l'interêt
        int bestQuery = findMaxInterestQuery(ist, solution);
        while (bestQuery != -1 && obj.distance(solution) <= ist.getMaxDistance() && obj.time(solution) <= ist.getTimeBudget()) {
            solution.add(bestQuery);
            bestQuery = findMaxInterestQuery(ist, solution);
        }

        // Retirer la dernière requête si elle dépasse les contraintes
        if (obj.distance(solution) > ist.getMaxDistance() || obj.time(solution) > ist.getTimeBudget()) {
            solution.remove(solution.size() - 1);
        }

        return solution;
    }

    private int findMaxInterestQuery(Instance ist, List<Integer> solution) {
        int bestQuery = -1;
        double maxInterest = 0;

        for (int i = 0; i < ist.getSize(); i++) {
            if (!solution.contains(i) && ist.getInterest()[i] > maxInterest) {
                maxInterest = ist.getInterest()[i];
                bestQuery = i;
            }
        }

        return bestQuery;
    }
}