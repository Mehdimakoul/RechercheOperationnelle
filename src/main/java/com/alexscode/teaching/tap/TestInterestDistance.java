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


            for (int i = 0; i < ist.getSize(); i++) {
                if (!demo.contains(i)) {
                    

                    double ratio = ist.getInterest()[i] * (1.0 / ist.getDistances()[i][q_idx]);

                    if (ratio > maxRatio) {
                        maxRatio = ratio;
                        q_idx = i;
                    }
                }
            }
            demo.add(q_idx);
        }
        

        demo.remove(demo.size() - 1);




        return demo;
    }
}

