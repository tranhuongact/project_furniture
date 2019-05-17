package application.controller.api;

import application.data.model.Product;
import application.data.model.Review;
import application.data.model.User;
import application.data.service.ProductService;
import application.data.service.ReviewService;
import application.data.service.UserService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ProductDTO;
import application.model.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/review")
public class ReviewApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/create")
    public BaseApiResult createReview(@RequestBody ReviewDTO dto){
        BaseApiResult result = new DataApiResult();
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        try {
                if(reviewService.checkUserReviewed(dto.getProductId(), userEntity.getId()).size() < 3){
                    Review review = new Review();
                    review.setCreatedDate(new Date());
                    review.setReview(dto.getReview());
                    review.setTitle(dto.getTitle());
                    review.setStar(dto.getStar());
                    review.setProduct(productService.findOne(dto.getProductId()));
                    review.setUser(userEntity);
                    reviewService.addReview(review);

                    result.setMessage("Thanks for your review!");
                    result.setSuccess(true);
                }else{
                    result.setMessage("please don't spam!");
                    result.setSuccess(false);
                }


        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
