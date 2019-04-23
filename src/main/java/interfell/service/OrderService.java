package interfell.service;

import interfell.bean.OrderDTO;
import interfell.bean.OrderDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by vsantos on 23/04/2019.
 */
public interface OrderService {

    public String saveOrder(OrderDTO orderDTO, String user);

    public List<OrderDTO> getOrders(Map<String, Object> map);

    public String saveProductToOrder(OrderDetailDTO orderDetailDTO,String user);

    public String updateProductToOrder(OrderDetailDTO orderDetailDTO,String user);

    public String deleteProductToOrder(OrderDetailDTO orderDetailDTO,String user);

    public Object priceCalculator(Map<String, Object> map);

    public long getIdForTest();

    public long getIdForTestOrderDetail();

}
