package interfell.bean;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

/**
 * Created by vsantos on 23/04/2019.
 */
public class OrderDetailDTO {

    private Long id;

    @Min(value = 1,message = "OrderId Should not be less than 1")
    private Long orderId;

    @Min(value = 1,message = "ProductId Should not be less than 1")
    private Long productId;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long id, Long orderId, Long productId) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
