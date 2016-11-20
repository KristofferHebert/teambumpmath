package com.example.myfirstapp;

import java.util.Arrays;
import java.util.List;

/**
 * Created by toddfrisch on 10/23/16.
 */

public class Problems {


    private static List<Problem> problems = Arrays.asList(
            new Problem(1, "40% of 240 is ?", "96", "I have no idea how to solve that either?!?"),
            new Problem(2, "4 x 4 = ?", "16", "4 + 4 + 4 + 4 = 16"),
            new Problem(3, "81 / 9  = ?", "9", "9 x 9 = 81")
    );

    public static Problem randomProblem(){
        return problems.get(RandomNumber.randInt(0, problems.size() -1));
    }

    public static Problem get(final int problemId){
        for(Problem problem : problems){
         if(problem.getId() == problemId) return problem;
        }
        throw new IllegalArgumentException("No problem matching that id");
    }

}
