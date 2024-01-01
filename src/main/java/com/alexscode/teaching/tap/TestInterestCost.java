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

        // Ajoute les requêtes dans une liste d'éléments
        for (int i = 0; i < ist.getSize(); i++) {
            demo.add(new Element(i, ist.getInterest()[i] / ist.getCosts()[i]));
        }

        // Trie la liste d'éléments en fonction de l'importance relative par rapport au coût
        Collections.sort(demo, Comparator.comparingDouble(Element::getValue).reversed());

        // Ajoute les requêtes tant qu'elles respectent les contraintes de temps et de distance
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