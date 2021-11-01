import org.junit.Assert;
import org.junit.Test;

import java.util.*;

//        Select the computational complexity of the stackToList method for
//        both possible values of revertitems argument.
//
//        (Select all acceptable answers.)
//        revertitems is false: 0(1)
//        revertitems is false: O(n)

public class StackToList {
    public static void main(String[] args) {
        // your code goes here
        System.out.println("StackToList");
    }

    public static <T> ArrayList<T> stackTolist(Stack<T> stack, boolean revertItems) {
        ArrayList<T> items = new ArrayList<T>();
        while (stack.size() > 0) {
            T item = stack.pop();
            if (revertItems) {
                items.add(0, item);
            } else {
                items.add(item);
            }
        }
        return items;
    }

    @Test
    public void stackTolist() {
        boolean revertItems = false;
        Stack<?> stack = new Stack<>();
        ArrayList<?> result = StackToList.stackTolist(stack, revertItems);
        Assert.assertNotNull(result);
    }
}
