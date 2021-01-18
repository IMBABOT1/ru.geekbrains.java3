package Generic;

public class Swap<T> {
    public static void swap (Object[]arr, int first, int second){
        Object element = arr[first];
        arr[first] = arr[second];
        arr[second] = element;
    }

    public T[] swap1(T[]arr, int first, int second){
        T element = arr[first];
        arr[first] = arr[second];
        arr[second] = element;
        return arr;
    }


}
