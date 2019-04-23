package interfell.controllers;

import interfell.bean.JsonResult;
import interfell.bean.OrderDTO;
import interfell.bean.OrderDetailDTO;
import interfell.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by vsantos on 23/04/2019.
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    JsonResult addOrder(@RequestHeader("User") String user, @Valid @RequestBody OrderDTO orderDTO) {
        try {
            String response = orderService.saveOrder(orderDTO,user);
            if (response == null) {
                return new JsonResult(true, null, "Successful Create.");
            } else {
                return new JsonResult(false, null, response);
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path = "/product",method = RequestMethod.POST)
    public @ResponseBody
    JsonResult addProductToOrder(@RequestHeader("User") String user, @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            String response = orderService.saveProductToOrder(orderDetailDTO,user);
            if (response == null) {
                return new JsonResult(true, null, "Successful Create.");
            } else {
                return new JsonResult(false, null, response);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path = "/product",method = RequestMethod.PUT)
    public @ResponseBody
    JsonResult updateProductToOrder(@RequestHeader("User") String user, @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            String response = orderService.updateProductToOrder(orderDetailDTO,user);
            if (response == null) {
                return new JsonResult(true, null, "Successful Update.");
            } else {
                return new JsonResult(false, null, response);
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path = "/product",method = RequestMethod.DELETE)
    public @ResponseBody
    JsonResult deleteProductToOrder(@RequestHeader("User") String user, @RequestBody OrderDetailDTO orderDetailDTO) {
        try{
            String result = orderService.deleteProductToOrder(orderDetailDTO,user);
            if(result == null){
                return new JsonResult(true, null, "Successful delete.");
            }else {
                return new JsonResult(false, null, result);
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    JsonResult getProducts(@RequestHeader("User") String user, @RequestParam Map<String, Object> map) {
        try{
            List<OrderDTO> orderDTOList = orderService.getOrders(map);
            if(orderDTOList == null){
                return new JsonResult(false, null, "Error to get the order.");
            }else {
                return new JsonResult(true, orderDTOList, "");
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path = "/priceCalculator",method = RequestMethod.GET)
    public @ResponseBody
    Object priceCalculator(@RequestHeader("User") String user, @RequestParam Map<String, Object> map) {
        try{
            Object res = orderService.priceCalculator(map);
            return res;
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }
}
