package com.alexscode.teaching.tap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearestNeighbor implements TAPSolver {

    @Override
    public List<Integer> solve(Instance ist) {
        Objectives obj = new Objectives(ist);
        List<Integer> solution = new ArrayList<>();

        List<Integer> unvisitedNodes = new ArrayList<>();
        for (int i = 0; i < ist.size; i++)
            unvisitedNodes.add(i);

        int currentNode = (int) (Math.random() * ist.size);
        solution.add(currentNode);
        unvisitedNodes.remove(Integer.valueOf(currentNode));

        while (!unvisitedNodes.isEmpty() &&
                (obj.time(solution) < ist.timeBudget && obj.distance(solution) < ist.maxDistance)) {
            int nearestNode = findNearestNode(currentNode, unvisitedNodes, ist);
            solution.add(nearestNode);
            unvisitedNodes.remove(Integer.valueOf(nearestNode));
            currentNode = nearestNode;
        }

        return solution.subList(0, solution.size() - 1);
    }

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