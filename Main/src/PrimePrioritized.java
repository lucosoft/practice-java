import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrimePrioritized {
    public static void main(String[] args) {
        System.out.println("PrimePrioritized");
    }

    public List<String> prioritizedOrders(int numOrders, List<String> orderList)
    {
        List<List<String>> auxList = new LinkedList<>();
//        List<String> auxAuxList = new LinkedList<>();
        for(int i=0;i<numOrders;i++){
            String[] arr = orderList.get(i).split(" ");
            List<String> auxAuxList = new LinkedList<>();

            for(int j=0;j<arr.length;j++){
                auxAuxList.add(arr[j]);
            }
            auxList.add(auxAuxList);
//            auxAuxList.clear();
        }

        List<String> outputList = new LinkedList<>();
        List<String> primeList = new LinkedList<>();
        List<String> noPrimeList = new LinkedList<>();
        for(int k=0;k<numOrders;k++){
            if(isNumeric(auxList.get(k).get(1))){
                noPrimeList.add(orderList.get(k));
            }
            else{
                primeList.add(orderList.get(k));
            }
        }

//        List<List<String>> primeAuxList = new LinkedList<>();
//        for(int l=0;l<primeList.size();l++){
//            String parts[] = primeList.get(l).split(" ", 3);
//            primeAuxList.put(l,parts[1]);
//        }


//        LinkedHashMap<Integer,String> primeAuxList = new LinkedHashMap<>();
//        for(int l=0;l<primeList.size();l++){
//            String parts[] = primeList.get(l).split(" ", 2);
//            primeAuxList.put(l,parts[1]);
//        }
//        Map<Integer, String> sortedMap =
//                primeAuxList.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                                (e1, e2) -> e1, LinkedHashMap::new));
//
//        sortedMap.forEach((key,value) -> outputList.add(primeList.get(key)));

        List<List<String>> primeAuxList = new LinkedList<>();
        for(int l=0;l<primeList.size();l++){
            List<String> primeAux2List = new LinkedList<>();
            String parts[] = primeList.get(l).split(" ", 2);
            primeAux2List.add(parts[0]);
            primeAux2List.add(parts[1]);
            primeAuxList.add(l,primeAux2List);
        }

        primeAuxList.sort((p1, p2) -> {
            if (p1.get(1).compareTo(p2.get(1)) == 0) {
                return p1.get(0).compareTo(p2.get(0));
            } else {
                return p1.get(1).compareTo(p2.get(1));
            }
        });

//        Map<Integer, String> sortedMap =
//                primeAuxList.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                                (e1, e2) -> e1, LinkedHashMap::new));

//        sortedMap.forEach((key,value) -> outputList.add(primeList.get(key)));

        primeAuxList.forEach(v->outputList.add(v.get(0)+" "+v.get(1)));
        for(int m=0;m<noPrimeList.size();m++){
            outputList.add(noPrimeList.get(m));
        }

        return outputList;
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
    public void calculate_test1() {
        List<String> inputList = new LinkedList<>();
        inputList.add("zld 93 12");
        inputList.add("fp kindle book");
        inputList.add("10a echo show");
        inputList.add("17g 12 25 6");
        inputList.add("abl kindle book");
        inputList.add("125 echo dot second generation");

        List<String> outputList = new LinkedList<>();
        outputList.add("125 echo dot second generation");
        outputList.add("10a echo show");
        outputList.add("abl kindle book");
        outputList.add("fp kindle book");
        outputList.add("zld 93 12");
        outputList.add("17g 12 25 6");

        assertEquals(outputList, prioritizedOrders(6, inputList));
    }

//    @Test
//    public void calculate_test2() {
//
//        assertEquals(475020, calculate(30, 7));
//    }
//
//    @Test
//    public void calculate_test3() {
//
//        assertEquals(2598960, calculate(52, 5));
//    }

//    @Test
//    public void calculate_test2() {
//
//        int[] mArray = {3, 2};
//        assertEquals("1" + System.lineSeparator() + "", calculate(3, 2, mArray));
//    }
//
//    @Test
//    public void calculate_test3() {
//
//        int[] mArray = {3, 8};
//        assertEquals("1 4 6" + System.lineSeparator() + "2 5 7", calculate(8, 2, mArray));
//    }
}
