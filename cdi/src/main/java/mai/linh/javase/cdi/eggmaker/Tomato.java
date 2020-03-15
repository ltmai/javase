package mai.linh.javase.cdi.eggmaker;

import javax.enterprise.context.Dependent;

@Dependent
public class Tomato implements Vegetable {
    
    @Override
    public String toString() {
        return "Tomato";
    }

}