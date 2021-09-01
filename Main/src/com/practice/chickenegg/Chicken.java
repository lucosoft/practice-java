package com.practice.chickenegg;

/*
Add the missing code to Chicken and Egg so the following actions are
completed:
• Chicken implements the Bird interface.
• A Chicken lays an egg that will hatch into a new Chicken.
• Eggs from other types of birds should hatch into a new bird of their
parent type.
• Hatching an egg for the second time throws a IllegalStateException.
 */

class Chicken implements Bird{
    private static String CHICKEN = "chicken";
    public Chicken() {
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Chicken running...");
        Chicken chicken = new Chicken();
        System.out.println(chicken instanceof Bird);
        Egg egg = chicken.lay();
        Bird bird = egg.hatch();
        System.out.println(bird.getClass());
    }

    @Override
    public Egg lay() {
        return Egg.getInstance(CHICKEN);
    }
}


