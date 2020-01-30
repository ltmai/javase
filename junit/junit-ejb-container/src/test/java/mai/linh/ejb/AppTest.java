package mai.linh.ejb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * EJB container test
     */
    @Test
    public void testApp() throws NamingException {

        EJBContainer ejbC = EJBContainer.createEJBContainer();
        Context ctx = ejbC.getContext();
    
        EchoBean bean = (EchoBean) ctx.lookup("java:global/classes/MyBean");
    
        assertNotNull(bean);
    
        String expected = "Hello EJB";
        String echoed = bean.echo(expected);
    
        assertNotNull(echoed);
        assertTrue(echoed.endsWith(expected));
    
        ejbC.close();
    }
}
