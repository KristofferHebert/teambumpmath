package com.example.myfirstapp;

/**
 * Created by toddfrisch on 10/23/16.
 */

public class RandomNumber {


    public static int randInt(int min, int max) {
        java.util.Random rand = new java.util.Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
