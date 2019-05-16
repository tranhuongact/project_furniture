package application.data.repository;

import application.data.model.Review;
import application.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from dbo_review_product r where r.productId = :productId")
    Page<Review> getListReviewProductByProduct(Pageable pageable, @Param("productId") Integer productId);

    @Query("select r from dbo_review_product r where r.productId = :productId and r.userId = :userId")
    List<Review> checkUserReviewed (@Param("productId") Integer productId, @Param("userId") Integer userId);

    @Query(value = "select avg(star) " +
            "from dbo_review_product r " +
            "where r.product_id=:productId",nativeQuery = true)
    Float getRateAvg(@Param("productId") Integer productId);

    @Query("select count(r.star) from dbo_review_product r where r.productId = :productId")
    Integer getTotalRating(@Param("productId") Integer productId);

    @Query("select count(r.star) from dbo_review_product r where r.productId = :productId and star = :orderOfStar")
    Integer getNumOfRatingEachStar(@Param("productId") Integer productId, @Param("orderOfStar") Integer orderOfStar);
}
