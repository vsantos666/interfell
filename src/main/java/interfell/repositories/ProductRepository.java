package interfell.repositories;


import interfell.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vsantos on 23/04/2019.
 */
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    public Product findByIdAndDisabled(@Param("id") long id,@Param("disabled") boolean disabled);

    public List<Product> findAllByDisabled(@Param("disabled") boolean disabled, Pageable pageable);

    @Query("SELECT max(a.id) FROM Product a WHERE a.disabled = false")
    public long findMaxProduct();

}
