package interfell.service.impl;

import interfell.bean.ProductDTO;
import interfell.model.Product;
import interfell.repositories.ProductRepository;
import interfell.service.ProductService;
import interfell.utils.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vsantos on 23/04/2019.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Method to save a new product
     * @param productDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String saveProduct(ProductDTO productDTO, String user) {
        try {
            Product product = MapperUtil.mapObject(productDTO, Product.class);
            product.setCreatedBy(user);
            productRepository.saveAndFlush(product);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to update a product
     * @param productDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String updateProduct(ProductDTO productDTO, String user) {
        try {
            Product product1= productRepository.findByIdAndDisabled((long)productDTO.getId(),false);
            if(product1 !=null){
                product1.setSKU(productDTO.getSKU());
                product1.setName(productDTO.getName());
                product1.setPrice(productDTO.getPrice());
                product1.setUpdatedBy(user);
                productRepository.save(product1);
                return null;
            }else{
                return "There is no Product";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    /**
     * Method to delete a product
     * @param productDTO
     * @param user
     * @return
     */
    @Transactional
    @Override
    public String deleteProduct(ProductDTO productDTO, String user) {
        try {
            Product product= productRepository.findByIdAndDisabled((long)productDTO.getId(),false);
            if(product !=null){
                product.setDisabled(true);
                product.setUpdatedBy(user);
                productRepository.saveAndFlush(product);
                return null;
            }else{
                return "There is no Product";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to get all products
     * @param map
     * @return
     */
    @Override
    public List<ProductDTO> getProducts(Map<String, Object> map) {
        try {
            Pageable pageable = new PageRequest(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("size").toString()));
            List<Product> productList = productRepository.findAllByDisabled(false,pageable);
            List<ProductDTO> productDTOList = productList
                    .stream()
                    .map(e -> MapperUtil.mapObject(e, ProductDTO.class))
                    .collect(Collectors.toList());
            return productDTOList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to get the id of product for test*
     * @return de id
     */
    @Transactional
    @Override
    public long getIdForTest() {
        try {
            long res = productRepository.findMaxProduct();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }



}
