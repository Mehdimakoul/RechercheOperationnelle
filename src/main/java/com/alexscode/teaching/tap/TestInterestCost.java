package com.alexscode.teaching.tap;
import com.alexscode.teaching.utilities.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestInterestCost implements TAPSolver {

    @Override
    public List<Integer> solve(Instance ist) {
        
        List<Element> demo = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        Objectives obj = new Objectives(ist);




        for (int i = 0; i < ist.getSize(); i++) {
            demo.add(new Element(i, ist.getInterest()[i] / ist.getCosts()[i]));
        }
        Collections.sort(demo, Comparator.comparingDouble(Element::getValue).reversed());

        



        while (obj.distance(result) < ist.getMaxDistance() && obj.time(result) < ist.getTimeBudget()) {
            for (Element element : demo) {
                
                if (!result.contains(element.getIndex())) {
                    result.add(element.getIndex());
                    break;
                }
            }
        }

        result.remove(result.size() - 1);
        return result;
    }
}