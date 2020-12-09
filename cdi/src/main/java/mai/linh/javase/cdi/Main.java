package mai.linh.javase.cdi;

import javax.enterprise.inject.Produces;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import mai.linh.javase.cdi.eggmaker.EggMaker;
import mai.linh.javase.cdi.eggmaker.OmeletteMaker;

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

    /**
     * EggMaker producer method.
     * Using Apache Deltaspike {@link BeanProvider#injectFields(Object)} 
     * to initialize the object created with new.
     * @see https://deltaspike.apache.org/documentation/core.html 
     * @return the EggMaker instance
     */
    @Produces
    public EggMaker eggMakerProducer() {
        System.out.println("Creating EggMaker with Producer method");
        EggMaker eggMaker =  new OmeletteMaker();
        BeanProvider.injectFields(eggMaker);
        return eggMaker;
    }
}
