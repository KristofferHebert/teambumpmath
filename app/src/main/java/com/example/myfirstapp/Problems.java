package com.example.myfirstapp;

import java.util.Arrays;
import java.util.List;

/**
 * Created by toddfrisch on 10/23/16.
 */

public class Problems {


    private static List<Problem> questions = Arrays.asList(
            new Problem("40% of 240 is ?", "96", "Craig wrote a bunch of crap"),
            new Problem("4 x 4 = ?", "16", "Step 1: get a calculator"),
            new Problem("81 / 9  = ?", "9", "9 x 9 = 81")
    );

    public static Problem randomQuestion(){
        return questions.get(RandomNumber.randInt(0, questions.size() -1));
    }

}
