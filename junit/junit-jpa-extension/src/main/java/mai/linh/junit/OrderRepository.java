package mai.linh.junit;

import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mai.linh.junit.order.Item;
import mai.linh.junit.order.Order;

public class OrderRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;
    
    public OrderRepository(EntityManager em) {
        this.em = em;
	}

    public void persist(Order order, Map<Long, Item> items) {
        em.persist(order);
        items.values().forEach(item -> item.setOrderId(order.getId()));
        order.setItems(items);
        em.merge(order);
    }

	public Stream<Order> findOrderByCustomer(String customer) {
        return em.createQuery("SELECT o FROM Order o WHERE o.customer = :customer", Order.class)
                 .setParameter("customer", customer) 
                 .getResultStream();
	}
}