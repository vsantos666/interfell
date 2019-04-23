package interfell.repositories;

import interfell.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vsantos on 23/04/2019.
 */
public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    public List<Order> findAllByDisabled(@Param("disabled") boolean disabled, Pageable pageable);

    public Order findByIdAndDisabled(@Param("id") long id,@Param("disabled") boolean disabled);

    @Query("SELECT max(a.id) FROM Order a WHERE a.disabled = false")
    public long findMaxOrder();

}
