package mai.linh.javase.cdi;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 * OmeletteMaker
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