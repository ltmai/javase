package mai.linh.junit.one_to_many.order;

import java.io.Serializable;

public class OrderItemPk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long orderId;

    private Long position;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long id) {
        this.orderId = id;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}