package application.controller.web;

import application.data.model.Blog;
import application.data.model.Category;
import application.data.model.OrderProduct;
import application.data.model.Product;
import application.data.service.BlogService;
import application.data.service.CategoryService;
import application.data.service.OrderProductService;
import application.data.service.ProductService;
import application.model.viewmodel.blog.BlogVM;
import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.home.BannerVM;
import application.model.viewmodel.home.HomeLandingVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends BaseController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private OrderProductService orderProductService;

    @GetMapping(value = "/")
    public String home (Model model,
                        @Valid @ModelAttribute("productname") ProductVM productName,
                        HttpServletResponse response,
                        HttpServletRequest request,final Principal principal){

        HomeLandingVM vm = new HomeLandingVM();

        /**
         * set list bannerVM
         */
        ArrayList<BannerVM> listBanners = new ArrayList<>();
        listBanners.add(new BannerVM("", "Luxurious","/images/slider/slider1.jpg"));
        listBanners.add(new BannerVM("", "Modern","/images/slider/slider2.jpg"));
        listBanners.add(new BannerVM("", "Enduring","/images/slider/slider3.jpg"));

        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVM.setShortDesc(category.getShortDesc());
            categoryVM.setMainImage(category.getMainImage());
            category.setCreatedDate(category.getCreatedDate());
            categoryVMList.add(categoryVM);
        }

        List<Blog> blogList = blogService.getListAllBlogs();
        List<BlogVM> blogVMList = new ArrayList<>();

        for (Blog blog : blogList){
            BlogVM blogVM = new BlogVM();
            if (blog.getUser() == null) {
                blogVM.setUserName("Unknown");
            } else {
                blogVM.setUserName(blog.getUser().getName());
            }
            blogVM.setTitle(blog.getTitle());
            blogVM.setSlug(blog.getSlug());
            blogVM.setMainImage(blog.getMainImage());
            blogVM.setShortDesc(blog.getShortDesc());
            blogVM.setCreatedDate(blog.getCreatedDate());

            blogVMList.add(blogVM);
        }

        //set list feature product
        List<OrderProduct> orderProductList = orderProductService.findTopFeatureProductByAmount();
        List<ProductVM> productVMList = new ArrayList<>();
        for(OrderProduct orderProduct : orderProductList){
            ProductVM productVM = new ProductVM();
            productVM.setMainImage(orderProduct.getProduct().getMainImage());
            productVM.setName(orderProduct.getProduct().getName());
            productVM.setId(orderProduct.getProduct().getId());
            productVM.setPrice(orderProduct.getProduct().getPrice());
            productVMList.add(productVM);
        }

        /**
         * set vm
         */
        vm.setProductVMList(productVMList);
        vm.setListBanners(listBanners);
        vm.setCategoryVMList(categoryVMList);
        vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));

        model.addAttribute("vm",vm);

        return "/home";
    }

    @GetMapping(value = "/product")
    public String home(Model model,
                       @Valid @ModelAttribute("productname") ProductVM productName,
                       @RequestParam(name = "categoryId", required = false) Integer categoryId,
//                       @RequestParam(name = "priceLower", required = false) Integer priceLower,
//                       @RequestParam(name = "priceUpper", required = false) Integer priceUpper,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "12") Integer size,
                       @RequestParam(name = "sortByPrice", required = false) String sort,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal){


        /**
         * check cookie to create cart guid
         */
//        this.checkCookie(response,request,principal);

        HomeLandingVM vm = new HomeLandingVM();

        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVM.setShortDesc(category.getShortDesc());
            categoryVM.setMainImage(category.getMainImage());
            category.setCreatedDate(category.getCreatedDate());
            categoryVMList.add(categoryVM);
        }

        /**
         * set list productVM
         */

        Sort sortable = new Sort(Sort.Direction.ASC,"id");
        if(sort != null) {
            if (sort.equals("ASC")) {
                sortable = new Sort(Sort.Direction.ASC,"price");
            }else {
                sortable = new Sort(Sort.Direction.DESC,"price");
            }
        }

        Pageable pageable = new PageRequest(page, size, sortable);

        Page<Product> productPage = null;

        if(categoryId != null) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,categoryId,null);
            Category category = categoryService.findOne(categoryId);
            vm.setKeyWord(category.getName());
        } else if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
            vm.setKeyWord("Find with key: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
        }

        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productPage.getContent()) {
            ProductVM productVM = new ProductVM();
            if(product.getCategory() == null) {
                productVM.setCategoryName("Unknown");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }
            if(product.getBrand() == null) {
                productVM.setBrandName("Unknown");
            } else {
                productVM.setBrandName(product.getBrand().getName());
            }
            productVM.setId(product.getId());
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
            productVM.setAmount(product.getAmount());
            productVM.setCreatedDate(product.getCreatedDate());

            productVMList.add(productVM);
        }

        long productTotal = productService.getTotalProducts();
        double maxPriceProduct = productService.getMaxPriceProduct();

        /**
         * set vm
         */

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));
        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        vm.setProductTotal(productTotal);
        vm.setMaxPriceProduct(maxPriceProduct);
        if(productVMList.size() == 0) {
            vm.setKeyWord("Not found any product");
        }

        model.addAttribute("vm",vm);
        model.addAttribute("page",productPage);
        return "/product";
    }

}