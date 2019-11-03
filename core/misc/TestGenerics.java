
import java.util.List;

class TestGenerics {

    public static void emptyList(List<?> list)
    {        
    }
    
    public static <T extends Comparable<T>> int countGreaterThan(T[] arr, T elem)
    {
        int count = 0;
        for (T t : arr)
        {
            if (t.compareTo(elem) > 0)
                count++;
        }
        return count;
    }

    public static void main(String args[])
    {        
    }
}