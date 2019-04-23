package interfell.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import interfell.model.base.BaseEntityAudit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vsantos on 23/04/2019.
 */
@Entity
@Table(name="ORDER",schema = "interfell")
@XmlRootElement
public class Order extends BaseEntityAudit {

    @Column(name="CODE", length = 100, nullable = false, unique = true)
    private String code;

    @Column(name = "TOTAL_PRICE", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "QUANTITY", precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "CURRENCY", precision = 10, scale = 2)
    private  String currency;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ORDER_ID")
    List<OrderDetail> detail;

    public Order() {
    }

    public Order(String code, BigDecimal totalPrice, BigDecimal quantity, String currency, List<OrderDetail> detail) {
        this.code = code;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.currency = currency;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public List<OrderDetail> getDetail() {
        return detail;
    }

    public void setOrderDetail(List<OrderDetail> detail) {
        this.detail = detail;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
