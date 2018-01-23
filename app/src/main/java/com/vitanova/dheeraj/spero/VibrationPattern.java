package com.vitanova.dheeraj.spero;

/**
 * Created by dheeraj on 21/1/18.
 */

public class VibrationPattern {
    public static long[] generate(String morse){
        int counter=0;
        int patternCounter=0;
        long[] pattern=new long[morse.length()];
        while(counter<morse.length()){
            if(morse.charAt(counter)=='/'&&morse.charAt(counter+1)=='/'&&morse.charAt(counter+2)=='/'){
                pattern[patternCounter]=1750;
                patternCounter++;
                counter+=3;
            }else if(morse.charAt(counter)=='/'&& morse.charAt(counter+1)=='/'){
                pattern[patternCounter]=750;
                patternCounter++;
                counter+=2;
            }else if(morse.charAt(counter)=='/'){
                pattern[patternCounter]=250;
                patternCounter++;
                counter++;
            }else if(morse.charAt(counter)=='.'){
                pattern[patternCounter]=250;
                patternCounter++;
                counter++;
            }else if(morse.charAt(counter)=='-'){
                pattern[patternCounter]=750;
                patternCounter++;
                counter++;
            }
        }
        return pattern;
    }
}
