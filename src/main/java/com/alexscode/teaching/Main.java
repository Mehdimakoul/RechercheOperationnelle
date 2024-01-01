package com.alexscode.teaching;
import com.alexscode.teaching.tap.Instance;
import com.alexscode.teaching.tap.TestTabuSearch;

import com.alexscode.teaching.tap.Objectives;
import com.alexscode.teaching.tap.TAPSolver;
import com.alexscode.teaching.tap.TestInterestCost;
import com.alexscode.teaching.tap.TestLocalSearch;

import java.util.List;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Instance f4_small = Instance.readFile("./instances/f4_tap_0_20.dat", 330, 27);

        Instance f4_1_big = Instance.readFile("./instances/f4_tap_1_400.dat", 6600, 540);
        Instance f4_4_big = Instance.readFile("./instances/f4_tap_4_400.dat", 6600, 540);
        Instance f1_3_big = Instance.readFile("./instances/f1_tap_3_400.dat", 6600, 540);
        Instance f1_9_big = Instance.readFile("./instances/tap_11_250.dat", 6600, 540);

        Objectives obj = new Objectives(f4_4_big);

        TAPSolver solver = new TestInterestCost();
        List<Integer> solution = solver.solve(f4_4_big);

        System.out.println("Interet: " + obj.interest(solution));
        System.out.println("Temps: " + obj.time(solution));
        System.out.println("Distance: " + obj.distance(solution));

        System.out.println("Feasible ? " + isSolutionFeasible(f4_4_big, solution));
    }

    public static boolean isSolutionFeasible(Instance ist, List<Integer> sol){
        Objectives obj = new Objectives(ist);
        return obj.time(sol) <= ist.getTimeBudget() && obj.distance(sol) <= ist.getMaxDistance() && sol.size() == (new TreeSet<>(sol)).size();
    }
}