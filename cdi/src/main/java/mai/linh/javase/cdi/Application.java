package mai.linh.javase.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Application
 */
@Singleton
public class Application {

    @Inject
    private Service service;

    @Inject 
    private EggMaker eggMaker;

    @PostConstruct
    public void init() {
        System.out.println("Initializing application");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Closing application");
    }

    public String toString() {
        return "Application : " + service.toString() + " - " + eggMaker.toString();
    }
}