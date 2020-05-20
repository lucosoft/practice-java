package com.practice;

import org.junit.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class PrimePrioritized2 {
    public static void main(String[] args) {
        System.out.println("PrimePrioritized");
    }

    public static List<String> getEventsOrder(String team1, String team2, List<String> events1, List<String> events2) {

        List<String> outList = new LinkedList<>();
        List<String> eventsAux1 = new LinkedList<>();
        List<String> eventsAux2 = new LinkedList<>();
        List<String> auxList0 = new LinkedList<>();
        List<String[]> auxList = new LinkedList<>();
        List<String> auxList2 = new LinkedList<>();
        events1.forEach(e->eventsAux1.add(team1 + " " + e));
        events2.forEach(e->eventsAux2.add(team2 + " " + e));
        eventsAux1.forEach(e->auxList.add(e.split(" ")));
        eventsAux2.forEach(e->auxList.add(e.split(" ")));
        eventsAux1.forEach(e->auxList0.add(e));
        eventsAux2.forEach(e->auxList0.add(e));
        AtomicInteger j = new AtomicInteger(0);
        auxList.forEach(e -> {

            for (int i = 0; i < e.length; i++) {
                if (e[i].contains("+")) {
                    if (isNumeric(e[i].substring(0, e[i].indexOf("+")))) {
                        auxList2.add(e[i].substring(0, e[i].indexOf("+")) + " " + auxList0.get(j.getAndIncrement()));
                    }
                } else {
                    if (isNumeric(e[i])) {
                        auxList2.add(e[i] + " " + auxList0.get(j.getAndIncrement()));
                    }
                }
            }
        });

        auxList2.sort(Comparator.comparingInt(x -> Integer.parseInt(x.split(" ")[0])));
        auxList2.forEach(v->outList.add(v.substring(v.indexOf(" ")+1, v.length())));

        return outList;
    }

    public static boolean isNumeric(String strNum) {

        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    @Test
    public void getEventsOrder_test1() {
        String team1 = "ABC";
        String team2 = "CBA";
        List<String> events1 = new LinkedList<>();
        events1.add("Mo Sa 45+2 Y");
        events1.add("A 13 G");

        List<String> events2 = new LinkedList<>();
        events2.add("D 23 S F");
        events2.add("Z 46 G");

        List<String> outputList = new LinkedList<>();
        outputList.add("ABC A 13 G");
        outputList.add("CBA D 23 S F");
        outputList.add("ABC Mo Sa 45+2 Y");
        outputList.add("CBA Z 46 G");

        assertEquals(outputList, getEventsOrder(team1, team2, events1, events2 ));
    }

}
