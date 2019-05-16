package application.data.repository;

import application.data.model.Order;
import application.data.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {
    @Query(value = "SELECT  DISTINCT * FROM dbo_order_product op where op.amount > 5" +
            " GROUP by op.product_id ORDER BY  op.amount ",nativeQuery = true)
    List<OrderProduct> findTopFeatureProductByAmount();
}
