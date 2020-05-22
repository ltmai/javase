package mai.linh.junit.one_to_many;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.extension.JpaUnitTestEm;
import mai.linh.junit.extension.JpaUnitTestExtension;
import mai.linh.junit.one_to_many.order.Item;
import mai.linh.junit.one_to_many.order.Order;
import mai.linh.junit.one_to_many.order.OrderRepository;

/**
 * JPA unit test
 * The following is necessary for JPA unit test:
 * 1. Annotate test class with @ExtendWith(JpaUnitTestExtension.class)
 * 2. A public EntityManager field annotated with @JpaUnitTest and @Spy
 * 3. The entity classes must be provided in test\resources\META-INF\persistence.xml
 */
@ExtendWith(JpaUnitTestExtension.class)
@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Spy
    @JpaUnitTestEm
    public EntityManager em;

    @InjectMocks
    private OrderRepository orderRepository;

    @Test
    public void whenOrderExists_thenItCanBeFound() {
        // given
        // when
        Optional<Order> firstEntry = orderRepository.allOrders().findFirst();
        // then
        assertAll("All orders",
            ()->assertTrue(firstEntry.isPresent()),
            ()->assertTrue(firstEntry.get().getItems().get(1L).getArticle().equals("HP Desktop PC"))
        );
    }   
    
    /**
     * Requirement/Purpose
     */
    @Test
    public void whenInsertOrder_thenNewOrderCreated() {
        // given
        Order order = new Order();
        order.setCustomer("NewCustomer");
        order.setDate(Calendar.getInstance().getTime());
        order.setStatus("New");

        Item item1 = new Item();
        item1.setPosition(1L);
        item1.setArticle("NewArticle1");
        item1.setAmount(1L);
        item1.setPrice(100L);

        Item item2 = new Item();
        item2.setPosition(2L);
        item2.setArticle("NewArticle2");
        item2.setAmount(1L);
        item2.setPrice(100L);
        Map<Long, Item> items = new HashMap<>();
        items.put(item1.getPosition(), item1);
        items.put(item2.getPosition(), item2);
        // when
        orderRepository.createOrder(order, items);
        // then
        assertAll("Create New order", 
            () -> assertEquals(items.size(), orderRepository.findOrderByCustomer("NewCustomer").findFirst().get().getItems().size())
        );
    }
}