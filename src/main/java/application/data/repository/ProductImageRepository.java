package application.data.repository;

import application.data.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository <ProductImage, Integer> {

    @Query("select count(pi.id) from dbo_product_image pi")
    long getTotalProductImages();

    @Query("SELECT pi FROM dbo_product_image pi " +
            "WHERE (:productId IS NULL OR (pi.productId = :productId))")
    Page<ProductImage> getListProductImageByProductContaining (Pageable pageable, @Param("productId") Integer productId);

    @Query("SELECT pi FROM dbo_product_image pi " +
            "WHERE (:productId IS NULL OR (pi.productId = :productId))")
    List<ProductImage> getListALlProductImageByProductContaining (@Param("productId") Integer productId);
}
