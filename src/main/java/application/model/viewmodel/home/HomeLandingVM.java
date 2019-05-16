package application.model.viewmodel.home;

import application.data.model.Blog;
import application.model.viewmodel.blog.BlogVM;
import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import application.model.viewmodel.common.ProductVM;

import java.util.List;

public class HomeLandingVM {
    private List<BannerVM> listBanners;
    private LayoutHeaderVM layoutHeaderVM;
    private List<ProductVM> productVMList;
    private List<CategoryVM> categoryVMList;
    private List<BlogVM> blogVMList;
    private long productTotal;
    private double maxPriceProduct;
    private String keyWord;

    public List<BannerVM> getListBanners() {
        return listBanners;
    }

    public void setListBanners(List<BannerVM> listBanners) {
        this.listBanners = listBanners;
    }

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }

    public List<ProductVM> getProductVMList() {
        return productVMList;
    }

    public void setProductVMList(List<ProductVM> productVMList) {
        this.productVMList = productVMList;
    }

    public List<CategoryVM> getCategoryVMList() {
        return categoryVMList;
    }

    public void setCategoryVMList(List<CategoryVM> categoryVMList) {
        this.categoryVMList = categoryVMList;
    }

    public List<BlogVM> getBlogVMList() {
        return blogVMList;
    }

    public void setBlogVMList(List<BlogVM> blogVMList) {
        this.blogVMList = blogVMList;
    }

    public long getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(long productTotal) {
        this.productTotal = productTotal;
    }

    public double getMaxPriceProduct() {
        return maxPriceProduct;
    }

    public void setMaxPriceProduct(double maxPriceProduct) {
        this.maxPriceProduct = maxPriceProduct;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
