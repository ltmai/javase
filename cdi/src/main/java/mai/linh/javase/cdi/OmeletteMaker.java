package mai.linh.javase.cdi;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 * OmeletteMaker
 * This is example of bean created by Producer method using new operator.
 * With standard CDI, its field (Vegetable in this case) will not be 
 * injected. However using Apache Deltaspike (CDI extensions), one can
 * inject fields programatically into the produced object (see Main.java).
 */
@Alternative
public class OmeletteMaker implements EggMaker {

    @Inject
    private Vegetable vegetable;

    @Override
    public String toString() {
        return "Omlelette : " + vegetable.toString();
    }
}