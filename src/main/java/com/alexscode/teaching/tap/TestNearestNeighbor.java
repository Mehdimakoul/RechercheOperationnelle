package com.alexscode.teaching.tap;
import java.util.ArrayList;
import java.util.List;

public class TestNearestNeighbor implements TAPSolver {

    @Override
    public List<Integer> solve(Instance ist) {


        
        Objectives obj = new Objectives(ist);
        List<Integer> solution = new ArrayList<>();
        List<Integer> pasvisitenodes = new ArrayList<>();


        for (int i = 0; i < ist.size; i++)
            pasvisitenodes.add(i);

        // choix noeud initial random
        int currentNode = (int) (Math.random() * ist.size);
        solution.add(currentNode);

        //supprimer le noeud initial 
        pasvisitenodes.remove(Integer.valueOf(currentNode));



        while (!pasvisitenodes.isEmpty() && (obj.time(solution) < ist.timeBudget && obj.distance(solution) < ist.maxDistance)) {

            int nearestNode = searchNearestNode(currentNode, pasvisitenodes, ist);
            
            solution.add(nearestNode);
            pasvisitenodes.remove(Integer.valueOf(nearestNode));
            currentNode = nearestNode;
        }


        return solution.subList(0, solution.size() - 1);
    }

    // Recherche du noeud le plus proche parmi les nœuds non visités
    private int searchNearestNode(int currentNode, List<Integer> pasvisitenodes, Instance ist) {
        int nearestNode = -1;
        double nearestDistance = Double.MAX_VALUE;

        for (int node : pasvisitenodes) {
            double distance = ist.distances[currentNode][node];
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNode = node;
            }
        }

        return nearestNode;
    }
}