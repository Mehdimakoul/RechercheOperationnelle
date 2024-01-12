package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.List;

public class BestQuery implements TAPSolver {
    @Override
    public List<Integer> solve(Instance ist) {
        List<Integer> solution = new ArrayList<>();
        Objectives obj = new Objectives(ist);

        int BQ = findMaxInterestQuery(ist, solution);
        while (BQ != -1 && obj.distance(solution) <= ist.getMaxDistance() && obj.time(solution) <= ist.getTimeBudget()) {
            solution.add(BQ);
            BQ = findMaxInterestQuery(ist, solution);
        }
        if (obj.distance(solution) > ist.getMaxDistance() || obj.time(solution) > ist.getTimeBudget()) {
            solution.remove(solution.size() - 1);
            }

        return solution;
    }

    private int findMaxInterestQuery(Instance ist, List<Integer> solution) {
        int BQ = -1;
        double maxInterest = 0;

        for (int i = 0; i < ist.getSize(); i++) {
            if (!solution.contains(i) && ist.getInterest()[i] > maxInterest) {
                maxInterest = ist.getInterest()[i];
                BQ = i;
            }
        }

        return BQ;
    }
}