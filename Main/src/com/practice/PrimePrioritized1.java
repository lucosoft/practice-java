package com.practice;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrimePrioritized1 {
    public static void main(String[] args) {
        System.out.println("PrimePrioritized");
    }

    static String findNumber(List<Integer> arr, int k) {

        String result = "no";

        if(arr.stream().anyMatch(a->a.equals(k))){
            result = "yes";
        }
        return result;
    }

    @Test
    public void findNumber_test1() {
        List<Integer> inputList = new LinkedList<>();
        inputList.add(1);
        inputList.add(2);
        inputList.add(3);
        inputList.add(4);
        inputList.add(5);

        assertEquals("yes", findNumber(inputList, 1));
    }

    @Test
    public void findNumber_test2() {
        List<Integer> inputList = new LinkedList<>();
        inputList.add(2);
        inputList.add(3);
        inputList.add(1);

        assertEquals("no", findNumber(inputList, 5));
    }

    static List<Integer> oddNumbers(int l, int r) {
        List<Integer> outputList = new LinkedList<>();
        for(int i=l;i<=r;i++){
            if(i%2!=0){
                outputList.add(i);
            }
        }
        return outputList;
    }

    @Test
    public void oddNumbers_test1() {
        List<Integer> outputList = new LinkedList<>();
        outputList.add(3);
        outputList.add(5);

        assertEquals(outputList, oddNumbers(2,5));
    }

}
