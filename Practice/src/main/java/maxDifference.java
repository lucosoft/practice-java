import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class maxDifference {
    public static void main(String[] args) {
        System.out.println("maxDifference");
    }

    static int calculate(List<Integer> arr) {
        int n = arr.get(0);
        arr.remove(0);
        List<List<Integer>> outList = new ArrayList<>();

        for(int j=0; j<arr.size();j++){
            List<Integer> auxList = new ArrayList<>();
            for(int i=j; i>=0; i--){
                if(arr.get(j)-arr.get(i)>0){
                    auxList.add(arr.get(j)-arr.get(i));
                }
            }
            if(auxList.size()>0){
                outList.add(auxList);
            }
        }

        if(outList.size()>0){
            List<Integer> auxList = new ArrayList<>();
            outList.forEach(v->v.sort(Comparator.<Integer>reverseOrder()));
            outList.forEach(v->auxList.add(v.get(0)));

            auxList.sort(Comparator.reverseOrder());
            return auxList.get(0);
        }
        else{
            return -1;
        }
    }

    @Test
    public void maxDifference_test1() {
        List<Integer> inputList = new LinkedList<>();
        inputList.add(7);
        inputList.add(2);
        inputList.add(3);
        inputList.add(10);
        inputList.add(2);
        inputList.add(4);
        inputList.add(8);
        inputList.add(1);

        Assert.assertEquals(8, calculate(inputList));
    }

    @Test
    public void maxDifference_test2() {
        List<Integer> inputList = new LinkedList<>();
        inputList.add(6);
        inputList.add(7);
        inputList.add(9);
        inputList.add(5);
        inputList.add(6);
        inputList.add(3);
        inputList.add(2);

        Assert.assertEquals(2, calculate(inputList));
    }

}
