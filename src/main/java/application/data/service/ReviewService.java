package application.data.service;

import application.data.model.Review;
import application.data.model.User;
import application.data.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> getListReviewProductByProduct(Pageable pageable, Integer productId){
        return reviewRepository.getListReviewProductByProduct(pageable, productId);
    }

    public void addReview(Review review){
        reviewRepository.save(review);
    }

    public List<Review> checkUserReviewed(Integer productId, Integer userId){
        return reviewRepository.checkUserReviewed(productId, userId);
    }
    public float getRateAvg(Integer productId){
        Float a = reviewRepository.getRateAvg(productId);
        if(a == null)
            return 0;
        return a;
    }
    public  int getNumOfRatingEachStar(Integer productId, Integer orderOfStar){
        Integer a = reviewRepository.getNumOfRatingEachStar(productId, orderOfStar);
        return a;
    }
    public float getPercentOfRatingEachStar(Integer productId, Integer orderOfStar){
        Integer a = reviewRepository.getTotalRating(productId);
        System.out.println(a);
        Integer b = reviewRepository.getNumOfRatingEachStar(productId, orderOfStar);

         Float c = new Float(((float)b/a) * 100);

        System.out.println(c);
        return c;
    }
}
