package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.extension.JpaJUnitEm;
import mai.linh.junit.extension.JpaJUnitExtension;
import mai.linh.junit.extension.TestNameExtension;
import mai.linh.junit.order.Item;
import mai.linh.junit.order.Order;

@ExtendWith(JpaJUnitExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(TestNameExtension.class)
public class OrderTest {

    // It would be prefect if we could do:
    //
    // @Spy
    // @JpaUnitTestEm
    // public EntityManager em;
    //
    // @InjectMocks
    // private OrderRepository orderRepository;
    //
    // However using @Spy and @InjectMocks to inject EntityManager into a DAO does
    // not work well with Hibernate if the entity contains a persistent collection. 
    //
    // Doing so will result in the following warning:
    // org.hibernate.collection.internal.AbstractPersistentCollection unsetSession
    // WARN: HHH000471: Cannot unset session in a collection because an unexpected
    // session is defined.
    // A persistent collection may only be associated with one session at a time.
    //
    // Because of the @Spy annotation Mockito creates a proxy to the EntityManager 
    // instance, which is then injected into OrderRepository. However when we call
    // EntityManager::close() on the original instance, Hibernate claims with this
    // warning.
    // NOTE: There is however no problem with Eclipselink.

    @JpaJUnitEm
    public EntityManager em;

    private OrderRepository orderRepository;

    @BeforeEach
    public void beforeAll() {
        orderRepository = new OrderRepository(em);
    }
        
    @Test
    public void whenAddingOrder_thenNewOrderCreated() {
        // given
        Order order = Order.of("Customer", Calendar.getInstance().getTime(), "New");
        Item item1 = Item.of(1L, "Article 1", 2L, 100L);
        Item item2 = Item.of(2L, "Article 2", 1L, 300L);
        Map<Long, Item> items = Stream.of(item1, item2).collect(Collectors.toMap(Item::getPosition, Function.identity()));
        // when
        orderRepository.persist(order, items);
        // then
        order = orderRepository.findOrderByCustomer("Customer").findFirst().get();
        assertEquals("Customer", order.getCustomer());
        assertEquals(2, order.getItems().size());
    }
}