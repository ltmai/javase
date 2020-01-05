package mai.linh.javase.cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        Application application = container.select(Application.class).get();
        System.out.println(application.toString());
        container.shutdown();
    }
}
