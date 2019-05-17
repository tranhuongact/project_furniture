package application.data.repository;

import application.data.model.Product;
import application.data.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    @Query("select count(p.id) from dbo_product p")
    long getTotalProducts();

    @Query("select max(p.price) from dbo_product p")
    Double getMaxPriceProduct();

    @Query("SELECT p FROM dbo_product p " +
            "WHERE (:categoryId IS NULL OR (p.categoryId = :categoryId))" +
            "AND (:productName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:productName),'%'))")
    Page<Product> getListProductByCategoryOrProductNameContaining(Pageable pageable, @Param("categoryId") Integer categoryId, @Param("productName") String productName);

    @Query("SELECT p FROM dbo_product p " +
            "WHERE p.price BETWEEN (:priceLower) AND (:priceUpper)")
    Page<Product> getListProductByPrice(Pageable pageable, @Param("priceLower") Double priceLower, @Param("priceUpper") Double priceUpper);

    @Query("select p from dbo_product p where p.categoryId = :categoryId")
    List<Product> getListProductByCategory(@Param("categoryId") Integer categoryId);
}