package com.practice.chickenegg;

import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertTrue;

public class Egg {

    private static final String CHICKEN = "chicken";
    private static Egg instance;
    public String value;

    public static Egg getInstance(String value) {
        if (instance == null) {
            instance = new Egg(value);
        }
        else {
            throw new IllegalStateException();
        }
        return instance;
    }

    private Egg(String value) {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.value = value;
    }

    public Egg (Callable<Bird> createBird) {
        throw new UnsupportedOperationException("Waiting to be implemented.");
    }

//    public Egg() {
//
//    }

    public Bird hatch() throws Exception {
        if(this.value.equalsIgnoreCase(CHICKEN)){
            return new Chicken();
        } else{
            return new Bird() {
                @Override
                public Egg lay() {
                    return null;
                }
            };
        }
    }

    @Test
    public void calculate_test1() {

        System.out.println("If you see the same value, then singleton was reused (yay!)" + "\n" +
                "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
                "RESULT:" + "\n");
        Egg egg = Egg.getInstance("FOO");
        Egg anotherSingleton = Egg.getInstance("BAR");
        System.out.println(egg.value);
        System.out.println(anotherSingleton.value);
        assertTrue(true);
    }
}
