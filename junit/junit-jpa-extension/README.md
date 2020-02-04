# JPA in unit test with JUnit

This project is an example of mocking an `EntityManager` in unit tests. An `EntityManager` is 
normally injected by the container into your class using annotation `@PersistenceContext`:

```java
public class PersonRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;

}
```

The medthods in a repostory class usually contains only JPA code and little bussiness logic. So it is difficult
to unit test PersonRepository e.g. making sure the queries are correct, if we do not have a real EntityManager.

In this example, we create a JUnit extension that instantiates an `EntityManager` to access an in-memory database
and injects it into the unit test class as a *public* property as follows:

```java
@ExtendWith(JpaUnitTestExtension.class)
@ExtendWith(MockitoExtension.class)
class JpaUnitTest {

    @Spy
    @JpaUnitTestEm
    public EntityManager em;

    @InjectMocks
    private PersonRepository personRepository;
}
```

The reason we need `@Spy` is because, we want this `EntityManager` to be injected by Mockito into the production
code under test, the `PersonRepository` class as in this case. (`@Spy` is intended for field only so we cannot 
create a stereotype combining `@Spy` and `@JpaUnitTestEm`, which would look much better).

Now if you change the query in method findPersonByLastName and make a typo there, the unit test will fail so you
know the problem immediately not until deployment.