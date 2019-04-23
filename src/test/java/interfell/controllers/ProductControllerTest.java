package interfell.controllers;

import interfell.bean.ProductDTO;
import interfell.configuration.*;
import interfell.service.ProductService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppInitializer.class, AppConfiguration.class,
        DozerConfiguration.class, PersistConfiguration.class, WebMvcConfiguration.class})
@WebAppConfiguration
public class ProductControllerTest extends TestCase {

    @Autowired
    private ProductService productService;

    public long productId=0;

    @Test
    public void test1_saveProduct() {
        ProductDTO productDTO= new ProductDTO();
        productDTO.setSKU(UUID.randomUUID().toString());
        productDTO.setName("BANANAS");
        productDTO.setPrice(new BigDecimal(60));
        String res = productService.saveProduct(productDTO,"test");
        assertEquals(null,res);
    }

    @Test
    public void test2_updateProduct() {
        productId = productService.getIdForTest();
        ProductDTO productDTO= new ProductDTO();
        productDTO.setId(productId);
        productDTO.setSKU(UUID.randomUUID().toString());
        productDTO.setName("BANANAS");
        productDTO.setPrice(new BigDecimal(30));
        String res = productService.updateProduct(productDTO,"test");
        assertEquals(null,res);
    }

    @Test
    public void test3_getProducts() {
        Map<String, Object> map = new HashMap<>();
        map.put("page",0);
        map.put("size",10);
        List<ProductDTO> productDTOList = productService.getProducts(map);
        assertNotNull(productDTOList);
    }

    @Test
    public void test4_deleteProduct() {
        productId = productService.getIdForTest();
        ProductDTO productDTO= new ProductDTO();
        productDTO.setId(productId);
        String res = productService.deleteProduct(productDTO,"test");
        assertEquals(null,res);
    }

}
