package Generic;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Swap<Integer> swap = new Swap<>();
        Integer[] arr = new Integer[]{1,2,3,43,5};
        swap.swap1(arr, 0, 4);
        System.out.println(Arrays.toString(arr));
        Swap.swap(arr, 2, 4);
        System.out.println(Arrays.toString(arr));
    }
}
