package interfell.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by vsantos on 23/04/2019.
 */
public class OrderDTO {

    private Long id;

    @NotBlank(message = "SKU cannot be null or blank")
    @Size(min = 5,max = 25,message = "SKU must be between 5 and 25 characters")
    private String code;

    @Digits(integer=6, fraction=2,message = "Price Should not be less than 6" )
    private BigDecimal totalPrice;

    @Digits(integer=6, fraction=2,message = "Price Should not be less than 6" )
    private BigDecimal quantity;

    @NotBlank(message = "Currency cannot be null or blank")
    @Size(min = 2,max = 5,message = "Currency must be between 2 and 5 characters")
    private String currency;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String code, BigDecimal totalPrice, BigDecimal quantity, String currency) {
        this.id = id;
        this.code = code;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
