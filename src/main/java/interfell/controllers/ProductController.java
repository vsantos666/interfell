package interfell.controllers;

import interfell.bean.JsonResult;
import interfell.bean.ProductDTO;
import interfell.service.ProductService;
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
@RequestMapping(value = "/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    JsonResult addProduct(@RequestHeader("User") String user, @Valid @RequestBody ProductDTO productDTO) {
        try {
            String response = productService.saveProduct(productDTO,user);
            if (response == null) {
                return new JsonResult(true, null, "Successful Create.");
            } else {
                return new JsonResult(false, null, response);
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    JsonResult updateProduct(@RequestHeader("User") String user, @Valid @RequestBody ProductDTO productDTO) {
        try{
            String result = productService.updateProduct(productDTO,user);
            if(result == null){
                return new JsonResult(true, null, "Successful update.");
            }else {
                return new JsonResult(false, null, result);
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody
    JsonResult deleteProduct(@RequestHeader("User") String user, @RequestBody ProductDTO productDTO) {
        try{
            String result = productService.deleteProduct(productDTO,user);
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
            List<ProductDTO> productDTOList = productService.getProducts(map);
            if(productDTOList == null){
                return new JsonResult(false, null, "Error to get the products.");
            }else {
                return new JsonResult(true, productDTOList, "");
            }
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

}
