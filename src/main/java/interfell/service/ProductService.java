package interfell.service;

import interfell.bean.ProductDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by vsantos on 23/04/2019.
 */
public interface ProductService {

    public String saveProduct(ProductDTO productDTO, String user);

    public String updateProduct(ProductDTO productDTO, String user);

    public String deleteProduct(ProductDTO productDTO, String user);

    public List<ProductDTO> getProducts(Map<String, Object> map);

    public long getIdForTest();


}
