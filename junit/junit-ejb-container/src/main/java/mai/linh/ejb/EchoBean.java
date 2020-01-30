package mai.linh.ejb;

import javax.ejb.Stateless;

/**
 * MyBean
 */
@Stateless
public class EchoBean {

    public String echo(String message){
        return "Echo: " + message;
    }
}