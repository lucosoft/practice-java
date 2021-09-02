import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CLEANUP {
    public static void main(String[] args) {
        // your code goes here
        System.out.println("CLEANUP");
        try {
            Scanner sc = new Scanner(System.in);
            int tc = sc.nextInt();
            while(tc-->0){
                int n = sc.nextInt();   // n is the total number of jobs that must be completed before closing
                int m = sc.nextInt();   // m is the number of jobs that have already been completed
                int[] mArray = new int[m];
                int pos = 0;
                while (pos < m) {
                    mArray[pos++] = sc.nextInt();
                }

                System.out.println(calculate(n, m, mArray));
//                tc--;
            }
        }
        catch(Exception e) {
            return;
        }
    }

    public static String calculate(int n, int m, int[] mArray){

        Arrays.sort(mArray);
        int pos = 0;
        int i = 1;
        boolean chefTurn = true;
        List<String> chefList = new LinkedList<>();
        List<String> chefAList = new LinkedList<>();
        while(i<=n) {
            if(pos == m || mArray[pos] != i) {
                if (chefTurn) {
                    chefList.add(Integer.toString(i));
                    chefTurn = false;
                } else {
                    chefAList.add(Integer.toString(i));
                    chefTurn = true;
                }
            }
            else{
                pos++;
            }
            i++;
        }
        return String.join(" ",chefList) + System.lineSeparator() + String.join(" ",chefAList);
    }

    @Test
    public void calculate_test1() {

        int[] mArray = {2, 4, 1};
        Assert.assertEquals("3 6" + System.lineSeparator() + "5", calculate(6, 3, mArray));
    }

    @Test
    public void calculate_test2() {

        int[] mArray = {3, 2};
        Assert.assertEquals("1" + System.lineSeparator() + "", calculate(3, 2, mArray));
    }

    @Test
    public void calculate_test3() {

        int[] mArray = {3, 8};
        Assert.assertEquals("1 4 6" + System.lineSeparator() + "2 5 7", calculate(8, 2, mArray));
    }
}
