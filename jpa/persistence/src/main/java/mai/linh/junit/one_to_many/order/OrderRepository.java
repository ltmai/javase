package mai.linh.junit.one_to_many.order;


import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * OrderRepository
 */
public class OrderRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;

    /**
     * supports unit test
     * @param em
     */
    public OrderRepository(EntityManager em) {
        this.em = em;
    }

	public void createOrder(Order order, Map<Long, Item> items) {
        em.persist(order);
        em.flush();
        items.values().stream().forEach(item -> item.setOrderId(order.getId()));
        order.setItems(items);
        em.flush();
    }

    public long numberOfOrders() {
        return (long)em.createQuery("SELECT COUNT(*) FROM Order ORDER BY ID").getSingleResult();
    }

    public Stream<Order> allOrders() {
        return em.createQuery("SELECT o FROM Order o", Order.class)
                 .getResultList().stream();
    }

    public Stream<Order> findOrderByCustomer(String customer) {
        return em.createQuery("SELECT o FROM Order o WHERE o.customer = :customer", Order.class)
                 .setParameter("customer", customer)
                 .getResultList().stream();
    }
}