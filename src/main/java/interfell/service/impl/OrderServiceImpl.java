package interfell.service.impl;

import interfell.bean.OrderDTO;
import interfell.bean.OrderDetailDTO;
import interfell.gateway.FixerApiGateway;
import interfell.model.Order;
import interfell.model.OrderDetail;
import interfell.model.Product;
import interfell.repositories.OrderDetailRepository;
import interfell.repositories.OrderRepository;
import interfell.repositories.ProductRepository;
import interfell.service.OrderService;
import interfell.utils.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vsantos on 23/04/2019.
 */
@Service
@PropertySources({
        @PropertySource("file:${fixer.property.file}")
})
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private Environment env;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private FixerApiGateway fixerApiGateway;

    /**
     * Method to save a new order
     * @param orderDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String saveOrder(OrderDTO orderDTO, String user) {
        try {
            Order order = MapperUtil.mapObject(orderDTO, Order.class);
            order.setCreatedBy(user);
            orderRepository.saveAndFlush(order);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to validate if exist a product with an order
     * @param producId
     * @param orderId
     * @return
     */
    public String validateExistingProduct(long producId, long orderId) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findOrderDetailByProductIdAndOrderId(producId,orderId,false);
            if(orderDetail!=null){
                return "Exist...";
            }else{
                OrderDetail orderDetail1 = orderDetailRepository.findOrderDetailByProductId(orderId,false);
                if(orderDetail!=null){
                    return "Exist for other Order";
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Method to update the order when a product wa added, updated or deleted
     * @param order
     * @param product
     * @param user
     * @param operation
     * @param lastPrice
     * @return
     */
    public String updateOrder(Order order,Product product, String user,char operation ,BigDecimal lastPrice) {
        try {
            switch (operation){
                case 'A':
                    order.setQuantity(order.getQuantity().add(new BigDecimal(1)));
                    order.setTotalPrice(order.getTotalPrice().add(product.getPrice()));
                    break;
                case 'U':
                    BigDecimal currentPrice = order.getTotalPrice().subtract(lastPrice);
                    order.setTotalPrice(currentPrice.add(product.getPrice()));
                    break;
                case 'D':
                    order.setQuantity(order.getQuantity().subtract(new BigDecimal(1)));
                    order.setTotalPrice(order.getTotalPrice().subtract(lastPrice));
                    break;
            }
            order.setUpdatedBy(user);
            orderRepository.save(order);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to save a product to an order
     * @param orderDetailDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String saveProductToOrder(OrderDetailDTO orderDetailDTO, String user) {
        try {
            //verify if product where added
            String val = validateExistingProduct(orderDetailDTO.getProductId(),orderDetailDTO.getOrderId());
            if(val != null){
                return val;
            }

            Order order = orderRepository.findByIdAndDisabled(orderDetailDTO.getOrderId(),false);
            Product product =  productRepository.findByIdAndDisabled(orderDetailDTO.getProductId(),false);
            if(product == null || order == null){
                return "there is not id of order or prduct";
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setCreatedBy(user);
            orderDetailRepository.saveAndFlush(orderDetail);

            //update order
            String res = updateOrder(order,product,user,'A',null);
            if(res != null){
                return res;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Method to update a product to an order
     * @param orderDetailDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String updateProductToOrder(OrderDetailDTO orderDetailDTO, String user) {
        try {
            //verify if product where added
            String val = validateExistingProduct(orderDetailDTO.getProductId(),orderDetailDTO.getOrderId());
            if(val != null){
                return val;
            }

            OrderDetail orderDetail = orderDetailRepository.findByIdAndDisabled(orderDetailDTO.getId(),false);
            if(orderDetail !=null){
                Product product =  productRepository.findByIdAndDisabled(orderDetailDTO.getProductId(),false);
                Order order = orderRepository.findByIdAndDisabled(orderDetailDTO.getOrderId(),false);
                if(product == null || order == null){
                    return "there is not id of order or prduct";
                }
                BigDecimal price = orderDetail.getProduct().getPrice();
                orderDetail.setProduct(product);
                orderDetail.setUpdatedBy(user);
                orderDetailRepository.saveAndFlush(orderDetail);

                //update order
                String res = updateOrder(order,product,user,'U',price);
                if(res != null){
                    return res;
                }
                return null;
            }else{
                return "There is no order";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to delete a product to an order
     * @param orderDetailDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String deleteProductToOrder(OrderDetailDTO orderDetailDTO, String user) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findByIdAndDisabled(orderDetailDTO.getId(),false);
            if(orderDetail !=null){
                Order order = orderRepository.findByIdAndDisabled(orderDetail.getOrder().getId(),false);
                if(order == null){
                    return "there is not id of order";
                }
                BigDecimal price = orderDetail.getProduct().getPrice();

                orderDetail.setDisabled(true);
                orderDetail.setUpdatedBy(user);
                orderDetailRepository.saveAndFlush(orderDetail);

                //update order
                String res = updateOrder(order,new Product(),user,'D',price);
                if(res != null){
                    return res;
                }
                return null;
            }else{
                return "There is no order";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to get orders
     * @param map
     * @return
     */
    @Override
    public List<OrderDTO> getOrders(Map<String, Object> map) {
        try {
            Pageable pageable = new PageRequest(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("size").toString()));
            List<Order> orderList = orderRepository.findAllByDisabled(false,pageable);
            List<OrderDTO> orderDTOList = orderList
                    .stream()
                    .map(e -> MapperUtil.mapObject(e, OrderDTO.class))
                    .collect(Collectors.toList());
            return orderDTOList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to calculate the price of an order in base of the currency
     * @param map
     * @return
     */
    @Override
    public Object priceCalculator(Map<String, Object> map) {
        try {
            Order order = orderRepository.findByIdAndDisabled(Long.parseLong(map.get("id").toString()),false);
            if(order == null){
                return "there is not id of order";
            }
            Object res = fixerApiGateway.priceCalculator(env.getProperty("fixer.url"),
                    env.getProperty("fixer.token"),order.getCurrency(),map.get("to").toString(),
                    order.getTotalPrice().toString());
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to get the id of order for test*
     * @return de id
     */
    @Transactional
    @Override
    public long getIdForTest() {
        try {
            long res = orderRepository.findMaxOrder();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Method to get the id of orderDetail for test*
     * @return de id
     */
    @Transactional
    @Override
    public long getIdForTestOrderDetail() {
        try {
            long res = orderDetailRepository.findMaxOrderDetail();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }
}
