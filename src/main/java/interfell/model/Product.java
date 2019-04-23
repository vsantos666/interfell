package interfell.model;


import interfell.model.base.BaseEntityAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * Created by vsantos on 23/04/2019.
 */
@Entity
@Table(name="PRODUCT",schema = "interfell")
@XmlRootElement
public class Product extends BaseEntityAudit {

    @Column(name="SKU", length = 100, nullable = false,unique = true)
    private String SKU;

    @Column(name="NAME", length = 250, nullable = false)
    private String name;

    @Column(name="PRICE", precision = 10, scale = 2)
    private BigDecimal price;

    public Product() {
    }

    public Product(String SKU, String name, BigDecimal price) {
        this.SKU = SKU;
        this.name = name;
        this.price = price;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
