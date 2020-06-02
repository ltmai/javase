package mai.linh.junit.order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="RETAIL_ORDER")
@Cacheable(false)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_ID")
    @SequenceGenerator(name = "SEQ_ORDER_ID", initialValue = 1000, allocationSize = 5 )
    private Long id;

    private String customer;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String status;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapKeyColumn(name = "POSITION", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false, referencedColumnName = "ID")
    private Map<Long, Item> items = new HashMap<Long, Item>();

    public static Order of(String customer, Date date, String status) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(date);
        order.setStatus(status);
        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<Long, Item> getItems() {
        return items;
    }

    public void setItems(Map<Long, Item> items) {
        this.items.clear();
        this.items.putAll(items);
    }
}