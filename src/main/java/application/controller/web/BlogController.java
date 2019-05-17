package application.controller.web;

import application.data.model.Blog;
import application.data.model.Category;
import application.data.model.User;
import application.data.service.BlogService;
import application.data.service.CategoryService;
import application.data.service.UserService;
import application.model.viewmodel.blog.BlogDetailVM;
import application.model.viewmodel.blog.BlogVM;
import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.user.UserVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/blog")
public class BlogController extends BaseController{

    private static final Logger logger = LogManager.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String blog (Model model,
                        @Valid @ModelAttribute("productname") ProductVM productName,
                        @Valid @ModelAttribute("title") BlogVM title,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        final Principal principal){

        BlogDetailVM vm = new BlogDetailVM();

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

        /**
         * set list userVM
         */
        List<User> userList = userService.getListAllUsers();
        List<UserVM> userVMList = new ArrayList<>();

        for (User user : userList){
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());

            userVMList.add(userVM);
        }

        Pageable pageable = new PageRequest(page, size);

        Page<Blog> blogPage = null;

        if (title.getTitle() != null && !title.getTitle().isEmpty()) {
            blogPage = blogService.getListBlogByUserOrTitleContaining(pageable,null, title.getTitle().trim());
            vm.setKeyWord("Find with key: " + title);
        } else {
            blogPage = blogService.getListBlogByUserOrTitleContaining(pageable,null,null);
        }

        List<BlogVM> blogVMList = new ArrayList<>();

        for (Blog blog : blogPage.getContent()){
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

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));
        vm.setCategoryVMList(categoryVMList);
        vm.setUserVMList(userVMList);
        vm.setBlogVMList(blogVMList);

        model.addAttribute("vm",vm);
        model.addAttribute("page", blogPage);

        return "/blog";
    }

    @GetMapping (value = "/{slug}")
    public String getDetailBlog (Model model,
                                 @Valid @ModelAttribute("productname") ProductVM productName,
                                 @Valid @ModelAttribute("title") BlogVM title,
                                 @PathVariable String slug,
                                 HttpServletResponse response,
                                 HttpServletRequest request,
                                 final Principal principal){

        BlogDetailVM vm = new BlogDetailVM();

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

        /**
         * set list userVM
         */
        List<User> userList = userService.getListAllUsers();
        List<UserVM> userVMList = new ArrayList<>();

        for (User user : userList){
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());

            userVMList.add(userVM);
        }

        Blog blog = blogService.getBlogDetailBySlug(slug);

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
        blogVM.setContent(blog.getContent());
        blogVM.setCreatedDate(blog.getCreatedDate());

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));
        vm.setCategoryVMList(categoryVMList);
        vm.setUserVMList(userVMList);

        model.addAttribute("vm",vm);
        model.addAttribute("blog", blogVM);

        return "/blog-detail";
    }
}
