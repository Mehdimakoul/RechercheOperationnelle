package com.alexscode.teaching.tap;
import java.util.ArrayList;
import java.util.List;

public class TestNearestNeighbor implements TAPSolver {

    @Override
    public List<Integer> solve(Instance ist) {
        // Initialisation des objectifs et de la solution
        Objectives obj = new Objectives(ist);
        List<Integer> solution = new ArrayList<>();

        // Initialisation de la liste des nœuds non visités
        List<Integer> unvisitedNodes = new ArrayList<>();
        for (int i = 0; i < ist.size; i++)
            unvisitedNodes.add(i);

        // Sélection d'un nœud initial de manière aléatoire
        int currentNode = (int) (Math.random() * ist.size);
        solution.add(currentNode);
        unvisitedNodes.remove(Integer.valueOf(currentNode));

        // Boucle principale de l'algorithme
        while (!unvisitedNodes.isEmpty() &&
                (obj.time(solution) < ist.timeBudget && obj.distance(solution) < ist.maxDistance)) {
            // Recherche du nœud le plus proche parmi les nœuds non visités
            int nearestNode = findNearestNode(currentNode, unvisitedNodes, ist);
            solution.add(nearestNode);
            unvisitedNodes.remove(Integer.valueOf(nearestNode));
            currentNode = nearestNode;
        }

        // Retourne la solution en enlevant le dernier nœud (redondant)
        return solution.subList(0, solution.size() - 1);
    }

    // Recherche du nœud le plus proche parmi les nœuds non visités
    private int findNearestNode(int currentNode, List<Integer> unvisitedNodes, Instance ist) {
        int nearestNode = -1;
        double nearestDistance = Double.MAX_VALUE;

        for (int node : unvisitedNodes) {
            double distance = ist.distances[currentNode][node];
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNode = node;
            }
        }

        return nearestNode;
    }
}