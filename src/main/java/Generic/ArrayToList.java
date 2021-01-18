package Generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToList<T> {
    public List<T> arrToList(T[]arr){
        List<T> list = new ArrayList<>(Arrays.asList(arr));
        return list;
    }
}
