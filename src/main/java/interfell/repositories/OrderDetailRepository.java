package interfell.repositories;

import interfell.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by vsantos on 23/04/2019.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, PagingAndSortingRepository<OrderDetail, Long> {

    @Query("SELECT a FROM OrderDetail a WHERE a.product.id = :productId and a.order.id = :orderId and  a.disabled = :disabled")
    public OrderDetail findOrderDetailByProductIdAndOrderId(@Param("productId") long productId,@Param("orderId") long orderId,
                                                          @Param("disabled") boolean disabled);

    @Query("SELECT a FROM OrderDetail a WHERE a.product.id = :productId and  a.disabled = :disabled")
    public OrderDetail findOrderDetailByProductId(@Param("productId") long productId, @Param("disabled") boolean disabled);

    public OrderDetail findByIdAndDisabled(@Param("id") long id,@Param("disabled") boolean disabled);

    @Query("SELECT max(a.id) FROM OrderDetail a WHERE a.disabled = false")
    public long findMaxOrderDetail();
}
