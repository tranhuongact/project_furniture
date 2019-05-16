package application.model.viewmodel.common;

import application.model.viewmodel.cart.CartProductVM;
import application.model.viewmodel.cart.CartVM;

import java.util.ArrayList;

public class LayoutHeaderVM {
    private String companyName;
    private String companyEmail;
    private String companyPhoneNumber;
    private ArrayList<HeaderMenuVM> headerMenuVMArrayList;
    private CartVM cartVM;

    public CartVM getCartVM() {
        return cartVM;
    }

    public void setCartVM(CartVM cartVM) {
        this.cartVM = cartVM;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public ArrayList<HeaderMenuVM> getHeaderMenuVMArrayList() {
        return headerMenuVMArrayList;
    }

    public void setHeaderMenuVMArrayList(ArrayList<HeaderMenuVM> headerMenuVMArrayList) {
        this.headerMenuVMArrayList = headerMenuVMArrayList;
    }
}
