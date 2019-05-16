package application.controller.web;

import application.constant.CompanyConstant;
import application.data.model.Cart;
import application.data.model.CartProduct;
import application.data.model.User;
import application.data.service.CartService;
import application.data.service.UserService;
import application.model.viewmodel.cart.CartProductVM;
import application.model.viewmodel.cart.CartVM;
import application.model.viewmodel.common.HeaderMenuVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    public CartVM getCartVM(HttpServletResponse response,
                            HttpServletRequest request,
                            final Principal principal){
        CartVM cartVM = new CartVM();
        this.checkCookie(response,request,principal);
        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String guid = getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if(guid != null) {
                Cart cartEntity = cartService.findFirstCartByGuid(guid);
                if(cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for(CartProduct cartProduct : cartEntity.getListCartProducts()) {
                        CartProductVM cartProductVM = new CartProductVM();
                        cartProductVM.setId(cartProduct.getId());
                        cartProductVM.setProductId(cartProduct.getProduct().getId());
                        cartProductVM.setProductName(cartProduct.getProduct().getName());
                        cartProductVM.setMainImage(cartProduct.getProduct().getMainImage());
                        cartProductVM.setAmount(cartProduct.getAmount());
                        double price = cartProduct.getAmount()*cartProduct.getProduct().getPrice();
                        cartProductVM.setPrice(df.format(price));
                        cartProductVM.setPricePerProduct(df.format(cartProduct.getProduct().getPrice()));
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        cartVM.setProductAmount(productAmount);
        cartVM.setCartProductVMS(cartProductVMS);
        cartVM.setTotalPrice(df.format(totalPrice));
        return cartVM;
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
    public LayoutHeaderVM getLayoutHeaderVM(HttpServletResponse response,
                                            HttpServletRequest request,
                                            final Principal principal){

        LayoutHeaderVM headerVM = new LayoutHeaderVM();
        ArrayList<HeaderMenuVM> headerMenuVMArrayList = new ArrayList<>();

        headerMenuVMArrayList.add(new HeaderMenuVM("Home", "/"));
        headerMenuVMArrayList.add(new HeaderMenuVM("Shop", "/product"));
        headerMenuVMArrayList.add(new HeaderMenuVM("Cart", "/cart"));
        headerMenuVMArrayList.add(new HeaderMenuVM("Blog", "/blog"));
        headerMenuVMArrayList.add(new HeaderMenuVM("Contact", "/contact"));
        //cart
        CartVM cartVM = new CartVM();
        this.checkCookie(response,request,principal);
        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String guid = getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if(guid != null) {
                Cart cartEntity = cartService.findFirstCartByGuid(guid);
                if(cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for(CartProduct cartProduct : cartEntity.getListCartProducts()) {
                        CartProductVM cartProductVM = new CartProductVM();
                        cartProductVM.setId(cartProduct.getId());
                        cartProductVM.setProductId(cartProduct.getProduct().getId());
                        cartProductVM.setProductName(cartProduct.getProduct().getName());
                        cartProductVM.setMainImage(cartProduct.getProduct().getMainImage());
                        cartProductVM.setAmount(cartProduct.getAmount());
                        double price = cartProduct.getAmount()*cartProduct.getProduct().getPrice();
                        cartProductVM.setPrice(df.format(price));
                        cartProductVM.setPricePerProduct(df.format(cartProduct.getProduct().getPrice()));
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        cartVM.setProductAmount(productAmount);
        cartVM.setCartProductVMS(cartProductVMS);
        cartVM.setTotalPrice(df.format(totalPrice));

        //
        headerVM.setHeaderMenuVMArrayList(headerMenuVMArrayList);
        headerVM.setCompanyName(CompanyConstant.name);
        headerVM.setCompanyEmail(CompanyConstant.email);
        headerVM.setCompanyPhoneNumber(CompanyConstant.phoneNumber);
        headerVM.setCartVM(cartVM);
        return headerVM;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {

        LayoutHeaderAdminVM vm = new LayoutHeaderAdminVM();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);

        if(userEntity != null) {
            vm.setUserName(username);
            if(userEntity.getAvatar() != null) {
                vm.setAvatar(userEntity.getAvatar());
            } else vm.setAvatar("https://aets.org.es/wp-content/uploads/2014/12/omita-el-icono-del-perfil-avatar-placeholder-gris-de-la-foto-99724602.jpg");
        }

        return vm;
    }
    public void checkCookie(HttpServletResponse response,
                            HttpServletRequest request,
                            final Principal principal) {
        Cookie cookie[] = request.getCookies();


        if(principal!= null) {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            Cart cartEntity = cartService.findByUserName(username);
            if(cartEntity != null) {
                Cookie cookie1 = new Cookie("guid",cartEntity.getGuid());
                cookie1.setPath("/");
                response.addCookie(cookie1);
            } else {
                UUID uuid = UUID.randomUUID();
                String guid = uuid.toString();
                Cart cart = new Cart();
                cart.setGuid(guid);
                cart.setUserName(username);
                cartService.addNewCart(cart);

                Cookie cookie2 = new Cookie("guid",guid);
                cookie2.setPath("/");
                response.addCookie(cookie2);
            }
        } else {
            boolean flag2 = true;

            String guid = null;

            if(cookie!=null) {
                for(Cookie c : cookie) {
                    if(c.getName().equals("guid")) {
                        flag2 = false;
                        guid = c.getValue();
                    }
                }
            }

            if(flag2 == true) {
                UUID uuid = UUID.randomUUID();
                String guid2 = uuid.toString();
                Cart cart2 = new Cart();
                cart2.setGuid(guid2);
                cartService.addNewCart(cart2);

                Cookie cookie3 = new Cookie("guid",guid2);
                cookie3.setPath("/");
                cookie3.setMaxAge(60*60*24);
                response.addCookie(cookie3);

            } else {
                Cart cartEntity = cartService.findFirstCartByGuid(guid);
                if(cartEntity == null) {
                    Cart cart3 = new Cart();
                    cart3.setGuid(guid);
                    cartService.addNewCart(cart3);
                }
            }

        }

    }


}
