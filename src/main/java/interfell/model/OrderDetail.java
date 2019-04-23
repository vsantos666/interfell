package interfell.model;

import interfell.model.base.BaseEntityAudit;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by vsantos on 23/04/2019.
 */
@Entity
@Table(name="ORDER_DETAIL",schema = "interfell")
@XmlRootElement
public class OrderDetail extends BaseEntityAudit {

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


