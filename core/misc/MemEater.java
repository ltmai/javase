import java.util.List;
import java.util.ArrayList;

public class MemEater {

    public static class X {        
    }

    /**
     * java MemEater <n> - number of millions of objects X
     */
    static public void main(String argv[]) {

        List<X> xs = new ArrayList<X>();
        int instanceCountInMeg = 0;
        try {
            instanceCountInMeg = Integer.valueOf(argv[0]);
        }
        catch (Exception e) {
            System.out.println("MemEater <n> - number of objects in millions");
        }
        int cnt = 0;
        System.out.println("Allocating memory ...");
        for (int i=0; i < instanceCountInMeg*1024; i++) {
            cnt++;
            System.out.println("cnt=" + cnt);
            for (int j=0; j < 1024; j++)
                xs.add(new X());
        }
        System.out.println("Memory allocated");
    }
}

