package application.controller.web;

import application.constant.RoleIdConstant;
import application.data.model.*;
import application.data.service.*;
import application.model.viewmodel.admin.*;
import application.model.viewmodel.blog.BlogVM;
import application.model.viewmodel.common.*;
import application.model.viewmodel.contact.ContactVM;
import application.model.viewmodel.order.OrderHistoryVM;
import application.model.viewmodel.order.OrderProductVM;
import application.model.viewmodel.order.OrderVM;
import application.model.viewmodel.user.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController extends BaseController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ContactService contactService;

    @GetMapping("")
    public String admin (Model model) {

        HomeAdminVM vm = new HomeAdminVM();

        List<ChartLabelDataVM> countProductByCategory = categoryService.countProductByCategory();
        List<ChartLabelDataVM> sumProductOrderByCategory = categoryService.sumProductOrderByCategory();

        ChartVM chartVM = new ChartVM();
        chartVM.setLabelDataList(countProductByCategory);

        ChartVM chartVM1 = new ChartVM();
        chartVM1.setLabelDataList(sumProductOrderByCategory);

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/login";
            }
        } else{
            return "redirect:/login";
        }

        vm.setCountProductByCategory(chartVM);
        vm.setSumProductOrderByCategory(chartVM1);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());

        model.addAttribute("vm",vm);
        return "/admin/index";
    }

    @GetMapping("/product")
    public String product(Model model) {

        AdminProductVM vm = new AdminProductVM();

        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }

        /* set list brandVM */
        List<Brand> brandList = brandService.getListAllBrands();
        List<BrandVM> brandVMList = new ArrayList<>();

        for (Brand brand : brandList){
            BrandVM brandVM = new BrandVM();
            brandVM.setId(brand.getId());
            brandVM.setName(brand.getName());

            brandVMList.add(brandVM);
        }

        /* set list userVM */
        List<User> userList = userService.getListAllUsers();
        List<UserVM> userVMList = new ArrayList<>();

        for (User user : userList){
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());

            userVMList.add(userVM);
        }

        /* set list productVM */
        List<Product> productList = productService.getListAllProducts();
        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productList) {
            ProductVM productVM = new ProductVM();
            if(product.getCategory() == null) {
                productVM.setCategoryName("Unknown");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }

            if(product.getUser() == null) {
                productVM.setUserName("Unknown");
            } else {
                productVM.setUserName(product.getUser().getName());
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
            productVM.setDescription(product.getDescription());
            productVM.setCreatedDate(product.getCreatedDate());;

            productVMList.add(productVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        vm.setBrandVMList(brandVMList);
        vm.setUserVMList(userVMList);

        model.addAttribute("vm",vm);

        return "/admin/product";
    }

    @GetMapping("/product/{productId}")
    public String product(Model model,
                          @PathVariable int productId) {

        AdminProductVM vm = new AdminProductVM();

        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }

        /* set list brandVM */
        List<Brand> brandList = brandService.getListAllBrands();
        List<BrandVM> brandVMList = new ArrayList<>();

        for (Brand brand : brandList){
            BrandVM brandVM = new BrandVM();
            brandVM.setId(brand.getId());
            brandVM.setName(brand.getName());

            brandVMList.add(brandVM);
        }

        /* set list userVM */
        List<User> userList = userService.getListAllUsers();
        List<UserVM> userVMList = new ArrayList<>();

        for (User user : userList){
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());

            userVMList.add(userVM);
        }

        Product product = productService.findOne(productId);
        ProductVM productVM = new ProductVM();

        if(product.getCategory() == null) {
            productVM.setCategoryName("Unknown");
        } else {
            productVM.setCategoryName(product.getCategory().getName());
        }

        if(product.getUser() == null) {
            productVM.setUserName("Unknown");
        } else {
            productVM.setUserName(product.getUser().getName());
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
        productVM.setDescription(product.getDescription());
        productVM.setCreatedDate(product.getCreatedDate());;

        /* set vm */
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setBrandVMList(brandVMList);
        vm.setUserVMList(userVMList);

        model.addAttribute("vm",vm);
        model.addAttribute("product", productVM);

        return "/admin/product_detail";
    }

    @GetMapping("/product-image")
    public String productImage(Model model) {

        AdminProductImageVM vm = new AdminProductImageVM();

        /**
         * set list productVM
         */
        List<Product> productList = productService.getListAllProducts();
        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productList) {
            ProductVM productVM = new ProductVM();
            productVM.setId(product.getId());
            productVM.setName(product.getName());
            productVMList.add(productVM);
        }

        List<ProductImage> productImageList = productImageService.getListAllProductImages();
        List<ProductImageVM> productImageVMList = new ArrayList<>();

        for (ProductImage productImage : productImageList){
            ProductImageVM productImageVM = new ProductImageVM();
            if(productImage.getProduct() == null) {
                productImageVM.setProductName("Unknown");
            } else {
                productImageVM.setProductName(productImage.getProduct().getName());
            }
            productImageVM.setId(productImage.getId());
            productImageVM.setLink(productImage.getLink());
            productImageVM.setCreatedDate(productImage.getCreatedDate());

            productImageVMList.add(productImageVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setProductVMList(productVMList);
        vm.setProductImageVMList(productImageVMList);

        model.addAttribute("vm", vm);

        return "/admin/productImage";
    }

    @GetMapping("/product-image/productId={productId}")
    public String getListProductImageByProductId(Model model,
                                                 @PathVariable int productId) {

        AdminProductImageVM vm = new AdminProductImageVM();

        List<ProductImage> productImageList = productImageService.getListAllProductImageByProductContaining(productId);
        List<ProductImageVM> productImageVMList = new ArrayList<>();

        for (ProductImage productImage : productImageList) {
            ProductImageVM productImageVM = new ProductImageVM();
            if(productImage.getProduct() == null) {
                productImageVM.setProductName("Unknown");
            } else {
                productImageVM.setProductName(productImage.getProduct().getName());
            }
            productImageVM.setId(productImage.getId());
            productImageVM.setLink(productImage.getLink());
            productImageVM.setCreatedDate(productImage.getCreatedDate());

            productImageVMList.add(productImageVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setProductImageVMList(productImageVMList);

        model.addAttribute("vm", vm);

        return "/admin/productImage";
    }

    @GetMapping("/category")
    public String category(Model model) {

        AdminCategoryVM vm = new AdminCategoryVM();

        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVM.setShortDesc(category.getShortDesc());
            categoryVM.setMainImage(category.getMainImage());
            categoryVM.setCreatedDate(category.getCreatedDate());

            categoryVMList.add(categoryVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);

        model.addAttribute("vm",vm);

        return "/admin/category";
    }

    @GetMapping("/blog")
    public String blog(Model model) {

        AdminBlogVM vm = new AdminBlogVM();

        List<User> userList = userService.getListAllUsers();
        List<UserVM> userVMList = new ArrayList<>();

        for (User user : userList){
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());

            userVMList.add(userVM);
        }

        List<Blog> blogList = blogService.getListAllBlogs();
        List<BlogVM> blogVMList = new ArrayList<>();

        for (Blog blog : blogList) {
            BlogVM blogVM = new BlogVM();
            if (blog.getUser() == null) {
                blogVM.setUserName("Unknown");
            } else {
                blogVM.setUserName(blog.getUser().getName());
            }
            blogVM.setId(blog.getId());
            blogVM.setTitle(blog.getTitle());
            blogVM.setSlug(blog.getSlug());
            blogVM.setShortDesc(blog.getShortDesc());
            blogVM.setMainImage(blog.getMainImage());
            blogVM.setContent(blog.getContent());
            blogVM.setCreatedDate(blog.getCreatedDate());

            blogVMList.add(blogVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setBlogVMList(blogVMList);
        vm.setUserVMList(userVMList);

        model.addAttribute("vm", vm);

        return "/admin/blog";
    }

    @GetMapping("/brand")
    public String brand(Model model){
        AdminBrandVM vm = new AdminBrandVM();

        List<Brand> brandList = brandService.getListAllBrands();
        List<BrandVM> brandVMList = new ArrayList<>();

        for(Brand brand : brandList){
            BrandVM brandVM = new BrandVM();
            brandVM.setId(brand.getId());
            brandVM.setName(brand.getName());
            brandVM.setAddress(brand.getAddress());
            brandVM.setEmail(brand.getEmail());
            brandVM.setPhoneNumber(brand.getPhoneNumber());
            brandVM.setCreatedDate(brand.getCreatedDate());

            brandVMList.add(brandVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setBrandVMList(brandVMList);

        model.addAttribute("vm", vm);

        return "/admin/brand";
    }

    @GetMapping("/order")
    public String order(Model model){

        AdminOrderVM vm = new AdminOrderVM();

//        List<OrderProduct> orderProductList = orderProductService.getListAllOrderProducts();
//        List<OrderHistoryVM> orderHistoryVMList = new ArrayList<>();
//
//
//        List<OrderProductVM> orderProductVMList = new ArrayList<>();
//
//        for(OrderProduct orderProduct : orderProductList){
//            OrderProductVM orderProductVM = new OrderProductVM();
//            orderProductVM.setProductId(orderProduct.getProductId());
//            orderProductVM.setOrderId(orderProduct.getOrderId());
//            orderProductVM.setProductName(orderProduct.getProduct().getName());
//            if(orderProduct.getOrder() == null){
//                orderProductVM.setCustomerName("Unknown");
//            } else {
//                orderProductVM.setCustomerName(orderProduct.getOrder().getCustomerName());
//            }
//
//            orderProductVM.setAmount(orderProduct.getAmount());
//            orderProductVM.setPrice(String.valueOf(orderProduct.getPrice()));
//            orderProductVM.setCreatedDate(orderProduct.getOrder().getCreatedDate());
//
//            orderProductVMList.add(orderProductVM);
//        }
//
//        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
//        vm.setOrderProductVMList(orderProductVMList);
        DecimalFormat df = new DecimalFormat("####0.00");
        List<Order> orderList = orderService.getListAllOrders();
        List<OrderVM> orderVMList = new ArrayList<>();
        for(Order order : orderList){
            OrderVM orderVM = new OrderVM();
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setEmail(order.getEmail());
            orderVM.setAddress(order.getAddress());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setPrice(df.format(order.getPrice()));
            orderVM.setCreatedDate(order.getCreatedDate());
            orderVM.setId(order.getId());
            orderVMList.add(orderVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setOrderVMList(orderVMList);
        model.addAttribute("vm", vm);

        return "/admin/order";
    }

    @GetMapping("/contact")
    public String contact(Model model){

        AdminContactVM vm = new AdminContactVM();

        List<Contact> contactList = contactService.getListAllContacts();
        List<ContactVM> contactVMList = new ArrayList<>();

        for (Contact contact : contactList){
            ContactVM contactVM = new ContactVM();
            contactVM.setId(contact.getId());
            contactVM.setFullName(contact.getFullName());
            contactVM.setPhoneNumber(contact.getPhoneNumber());
            contactVM.setEmail(contact.getEmail());
            contactVM.setMessage(contact.getMessage());
            contactVM.setCreatedDate(contact.getCreatedDate());

            contactVMList.add(contactVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setContactVMList(contactVMList);

        model.addAttribute("vm", vm);

        return "/admin/contact";
    }
}
