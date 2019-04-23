package interfell.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by vsantos on 23/04/2019.
 */
public class ProductDTO {

    private Long id;

    @NotBlank(message = "SKU cannot be null or blank")
    @Size(min = 5,max = 25,message = "SKU must be between 5 and 25 characters")
    private String SKU;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(min = 2,max = 250,message = "Name must be between 2 and 250 characters")
    private String name;

    @Digits(integer=6, fraction=2,message = "Price Should not be less than 6" )
    private BigDecimal price;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String SKU, String name, BigDecimal price) {
        this.id = id;
        this.SKU = SKU;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
