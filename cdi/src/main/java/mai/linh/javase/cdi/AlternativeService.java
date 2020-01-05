package mai.linh.javase.cdi;

import javax.enterprise.inject.Alternative;

@Alternative
public class AlternativeService implements Service {

    @Override
    public String toString() {
        return "Alternative service";
    }
}