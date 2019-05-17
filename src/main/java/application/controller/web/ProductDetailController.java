package application.controller.web;

import application.data.model.*;
import application.data.service.*;
import application.model.viewmodel.cart.CartProductVM;
import application.model.viewmodel.cart.CartVM;
import application.model.viewmodel.common.ProductImageVM;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.productdetail.ProductDetailVM;
import application.model.viewmodel.productdetail.RatingBreakdownVM;
import application.model.viewmodel.productdetail.ReviewVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/product")
public class ProductDetailController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Integer productId,
                                Model model, @Valid @ModelAttribute("productname")ProductVM productName,
                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name = "size", required = false, defaultValue = "3") Integer size,
                                @RequestParam(name = "sortByDate", required = false) String sort,
                                HttpServletResponse response,
                                HttpServletRequest request,
                                final Principal principal

                                ){

        ProductDetailVM vm = new ProductDetailVM();
        Product productEntity = productService.findOne(productId);
        //get cart by guid
//        int productAmount = 0;
//        double totalPrice = 0;
//        DecimalFormat df = new DecimalFormat("####0.00");
//        List<CartProductVM> cartProductVMS = new ArrayList<>();
//        this.checkCookie(response,request,principal);
//
//        Cookie cookie[] = request.getCookies();
//        String guid = null;
//        if(cookie!=null) {
//            for(Cookie c :cookie) {
//                if(c.getName().equals("guid"))
//                    guid = c.getValue();
//            }
//        }
//        Cart cartEntity = cartService.findFirstCartByGuid(guid);
//        if(cartEntity != null) {
//            productAmount = cartEntity.getListCartProducts().size();
//            for(CartProduct cartProduct : cartEntity.getListCartProducts()) {
//                CartProductVM cartProductVM = new CartProductVM();
//                cartProductVM.setId(cartProduct.getId());
//                cartProductVM.setProductId(cartProduct.getProduct().getId());
//                cartProductVM.setProductName(cartProduct.getProduct().getName());
//                cartProductVM.setMainImage(cartProduct.getProduct().getMainImage());
//                cartProductVM.setAmount(cartProduct.getAmount());
//                double price = cartProduct.getAmount()*cartProduct.getProduct().getPrice();
//                cartProductVM.setPrice(df.format(price));
//                cartProductVM.setPricePerProduct(df.format(cartProduct.getProduct().getPrice()));
//                totalPrice += price;
//                cartProductVMS.add(cartProductVM);
//            }
//        }
        //
        ProductVM productVM = new ProductVM();
        //get amount per product in cart
        this.checkCookie(response,request,principal);
        String guid = getGuid(request);
        Cart cartEntity = cartService.findFirstCartByGuid(guid);
        CartProductVM cartProductVM = new CartProductVM();
        CartProduct cartProduct = cartProductService.findFirstCartProductByCartIdAndProductId(cartEntity.getId(), productEntity.getId());
        if(cartProduct != null){
            cartProductVM.setAmount(cartProduct.getAmount());
        }

        //

        if(productEntity != null){
            productVM.setId(productEntity.getId());
            productVM.setName(productEntity.getName());
            productVM.setMainImage(productEntity.getMainImage());
            productVM.setShortDesc(productEntity.getShortDesc());
            productVM.setDescription(productEntity.getDescription());
            productVM.setPrice(productEntity.getPrice());
            productVM.setCategoryId(productEntity.getCategoryId());
            productVM.setCategoryName(productEntity.getCategory().getName());
            productVM.setAmount(productEntity.getAmount());
            int avgPoint = (int)reviewService.getRateAvg(productEntity.getId());
            productVM.setAvgPoint(avgPoint);
            //set list product image vm

            List<ProductImageVM> productImageVMS = new ArrayList<>();
            for(ProductImage productImage : productEntity.getProductImageList()){
                ProductImageVM productImageVM = new ProductImageVM();
                productImageVM.setLink(productImage.getLink());
                productImageVMS.add(productImageVM);
            }
            productVM.setProductImageVMS(productImageVMS);
        }
        List<Product> productList = productService.getListProductByCategory(productEntity.getCategoryId());
        List<ProductVM> productVMList = new ArrayList<>();
        for(Product product : productList){
            ProductVM productVM1 = new ProductVM();
            productVM1.setId(product.getId());
            productVM1.setName(product.getName());
            productVM1.setMainImage(product.getMainImage());
            productVM1.setPrice(product.getPrice());
            productVMList.add(productVM1);
        }
        //page review
        Sort sortable = new Sort(Sort.Direction.DESC,"createdDate");
        Pageable pageable = new PageRequest(page, size, sortable);
        Page<Review> reviewPage = null;
        reviewPage = reviewService.getListReviewProductByProduct(pageable, productId);
        List<ReviewVM> reviewVMList = new ArrayList<>();
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        //if(username != "anonymousUser"){
            //User userEntity = userService.findUserByUsername(username);
            for(Review review: reviewPage.getContent()){
                ReviewVM reviewVM = new ReviewVM();
                reviewVM.setId(review.getId());
                reviewVM.setCreatedDate(review.getCreatedDate());
                reviewVM.setReview(review.getReview());
                reviewVM.setStar(review.getStar());
                User userEntity = userService.findOne(review.getUserId());
                reviewVM.setUserImage(userEntity.getAvatar());
                reviewVM.setUserName(userEntity.getUserName());
                reviewVM.setTitle(review.getTitle());
                reviewVMList.add(reviewVM);
            }

//        }
//        else{
//            ReviewVM reviewVM = new ReviewVM();
//            reviewVM.setReview("Đăng nhập để xem bình luận");
//            reviewVMList.add(reviewVM);
//        }
        List<RatingBreakdownVM> ratingBreakdownVMList = new ArrayList<>();
        for(int i = 5; i > 0; i--){
            RatingBreakdownVM ratingBreakdownVM = new RatingBreakdownVM();
            ratingBreakdownVM.setNumOfRatingEachStar(reviewService.getNumOfRatingEachStar(productEntity.getId(), i));
            ratingBreakdownVM.setPercentOfRatingEachStar((int)reviewService.getPercentOfRatingEachStar(productEntity.getId(), i));
            ratingBreakdownVMList.add(ratingBreakdownVM);
        }

        //
        vm.setRatingBreakdownVMList(ratingBreakdownVMList);
        vm.setUserName(username);
        vm.setReviewVMList(reviewVMList);
        model.addAttribute("page", reviewPage);
        vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));
        vm.setProductVMList(productVMList);
        vm.setProductVM(productVM);
        vm.setCartProductVM(cartProductVM);
        //vm.setCartVM(this.getCartVM(response, request, principal));
        //vm.setCartProductVMS(cartProductVMS);
        model.addAttribute("vm", vm);

        return "/product-detail";
    }
    public String getGuid(HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();

        if(cookie!=null) {
            for(Cookie c :cookie) {
                if(c.getName().equals("guid"))  return c.getValue();
            }
        }
        return null;
    }
}

