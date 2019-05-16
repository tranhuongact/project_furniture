package application.model.viewmodel.productdetail;

import application.model.viewmodel.cart.CartProductVM;
import application.model.viewmodel.cart.CartVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import application.model.viewmodel.common.ProductVM;

import java.util.List;

public class ProductDetailVM {
    private ProductVM productVM;
    private LayoutHeaderVM layoutHeaderVM;
    private CartProductVM cartProductVM;
    private List<ReviewVM> reviewVMList;
    private List<ProductVM> productVMList;
    private List<RatingBreakdownVM> ratingBreakdownVMList;
    private String userName;
//    private List<CartProductVM> cartProductVMS;
//    private CartVM cartVM;
//
//    public List<CartProductVM> getCartProductVMS() {
//        return cartProductVMS;
//    }
//
//    public void setCartProductVMS(List<CartProductVM> cartProductVMS) {
//        this.cartProductVMS = cartProductVMS;
//    }
//
//    public CartVM getCartVM() {
//        return cartVM;
//    }
//
//    public void setCartVM(CartVM cartVM) {
//        this.cartVM = cartVM;
//    }


    public List<RatingBreakdownVM> getRatingBreakdownVMList() {
        return ratingBreakdownVMList;
    }

    public void setRatingBreakdownVMList(List<RatingBreakdownVM> ratingBreakdownVMList) {
        this.ratingBreakdownVMList = ratingBreakdownVMList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ReviewVM> getReviewVMList() {
        return reviewVMList;
    }

    public void setReviewVMList(List<ReviewVM> reviewVMList) {
        this.reviewVMList = reviewVMList;
    }

    public CartProductVM getCartProductVM() {
        return cartProductVM;
    }

    public void setCartProductVM(CartProductVM cartProductVM) {
        this.cartProductVM = cartProductVM;
    }



    public List<ProductVM> getProductVMList() {
        return productVMList;
    }

    public void setProductVMList(List<ProductVM> productVMList) {
        this.productVMList = productVMList;
    }



    public ProductVM getProductVM() {
        return productVM;
    }

    public void setProductVM(ProductVM productVM) {
        this.productVM = productVM;
    }

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }
}
