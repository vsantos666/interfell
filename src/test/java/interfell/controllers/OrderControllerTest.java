package interfell.controllers;

import interfell.bean.OrderDTO;
import interfell.bean.OrderDetailDTO;
import interfell.bean.ProductDTO;
import interfell.configuration.*;
import interfell.service.OrderService;
import interfell.service.ProductService;
import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderControllerTest extends TestCase {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Test
    public void test1_saveOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCode(UUID.randomUUID().toString());
        orderDTO.setCurrency("EUR");
        orderDTO.setQuantity(new BigDecimal(0));
        orderDTO.setTotalPrice(new BigDecimal(0));
        String res = orderService.saveOrder(orderDTO,"test");
        assertEquals(null,res);
    }

    @Test
    public void test2_saveProductToOrder() {
        //save a product for test
        ProductDTO productDTO= new ProductDTO();
        productDTO.setSKU(UUID.randomUUID().toString());
        productDTO.setName("ORANGES");
        productDTO.setPrice(new BigDecimal(40));
        productService.saveProduct(productDTO,"test");

        //get id from product and order
        long productId = productService.getIdForTest();
        long orderId = orderService.getIdForTest();

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderId(orderId);
        orderDetailDTO.setProductId(productId);
        String res = orderService.saveProductToOrder(orderDetailDTO,"test");
        assertEquals(null,res);
    }

    @Test
    public void test3_updateProductToOrder() {
        //save a product for test
        ProductDTO productDTO= new ProductDTO();
        productDTO.setSKU(UUID.randomUUID().toString());
        productDTO.setName("TOMATOES");
        productDTO.setPrice(new BigDecimal(30));
        productService.saveProduct(productDTO,"test");

        //get id from product and order
        long productId = productService.getIdForTest();
        long orderId = orderService.getIdForTest();
        long orderDetailId = orderService.getIdForTestOrderDetail();

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetailId);
        orderDetailDTO.setOrderId(orderId);
        orderDetailDTO.setProductId(productId);
        String res = orderService.updateProductToOrder(orderDetailDTO,"test");
        assertEquals(null,res);
    }

    @Test
    public void test4_getOrders() {
        Map<String, Object> map = new HashMap<>();
        map.put("page",0);
        map.put("size",10);
        List<OrderDTO> orderDTOList = orderService.getOrders(map);
        assertNotNull(orderDTOList);
    }

    @Test
    public void test5_priceCalculator() {
        long orderId = orderService.getIdForTest();

        Map<String, Object> map = new HashMap<>();
        map.put("id",orderId);
        map.put("to","USD");
        Object o = orderService.priceCalculator(map);
        assertNotNull(o);
    }

    @Test
    public void test6_deleteProductToOrder() {
        long orderDetailId = orderService.getIdForTestOrderDetail();

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetailId);
        String res = orderService.deleteProductToOrder(orderDetailDTO,"test");
        assertEquals(null,res);
    }
}
