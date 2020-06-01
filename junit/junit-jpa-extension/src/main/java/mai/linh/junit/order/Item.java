package mai.linh.junit.order;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Cacheable(value = false)
@IdClass(OrderItemPk.class)
public class Item {

    @Id
    @Column(name="ORDER_ID")
    private Long orderId;

    @Id
    private Long position;

    private String article;

    private Long amount;

    private Long price;

    public static Item of (Long position, String article, Long amount, Long price) {
        Item item = new Item();
        item.setPosition(position);
        item.setArticle(article);
        item.setAmount(amount);
        item.setPrice(price);
        return item;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
