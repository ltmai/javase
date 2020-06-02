# A JUnit 5 extension for testing JPA code

This project is an example of mocking an `EntityManager` in unit tests. An `EntityManager` is normally injected by 
the container into your class using annotation `@PersistenceContext`:

```java
public class PersonRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;

}
```

The medthods in a repostory class usually contains only JPA code and little bussiness logic. So it is difficult
to unit test PersonRepository e.g. making sure the queries are correct, if we do not test with a database.

In this example, we create a JUnit 5 extension that instantiates an `EntityManager` to access an in-memory database
and injects it into the unit test class as a *public* property as follows:

```java
@ExtendWith(JpaJUnitExtension.class)
@ExtendWith(MockitoExtension.class)
class JpaUnitTest {

    @Spy
    @JpaJUnitEm
    public EntityManager em;

    @InjectMocks
    private PersonRepository personRepository;
}
```

The reason we need `@Spy` is because, we want this `EntityManager` to be injected by Mockito into the production
code under test, the `PersonRepository` class as in this case. 

Now if you change the query in method `findPersonByLastName` and make a typo there, the unit test will fail so you
can detect the bug right in your unit tests.

# Limittations

For each test class, it creates an EntityManagerFactory to provide connections to an in memory database. This is 
done by registering to JUnit extension points via callbacks. It would be perfect if you could do this once for a 
single build (e.g. Maven build), however there is no extension point that we can register once when JUnit engine 
starts and once on complete. Therefore the extension creates a new EntityManagerFactory instance for each test.

The extension was tested with Hibernate and EclipseLink. With Hibernate however we get an warning when inject an
EntityManager as above if the entity contains a collection (List or Map). Mockito creates a proxy to the injected
Entitymanager, that for some reason causes this warning when we try closing the original EntityManager! There is 
an workaround for this as in `OrderTest.java`.