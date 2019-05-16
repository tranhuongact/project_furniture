package application.controller.api;

import application.data.model.Blog;
import application.data.service.BlogService;
import application.data.service.UserService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.BlogDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/blog")
public class BlogApiController {

    private static final Logger logger = LogManager.getLogger(BlogApiController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public BaseApiResult createBlog(@RequestBody BlogDTO dto) {
        DataApiResult result = new DataApiResult();

        try {
            Blog blogEntity = new Blog();
            blogEntity.setTitle(dto.getTitle());
            blogEntity.setSlug(dto.getSlug());
            blogEntity.setShortDesc(dto.getShortDesc());
            blogEntity.setMainImage(dto.getMainImage());
            blogEntity.setUser(userService.findOne(dto.getUserId()));
            blogEntity.setContent(dto.getContent());
            blogEntity.setCreatedDate(new Date());
            blogService.addNewBlog(blogEntity);

            result.setData(blogEntity.getId());
            result.setSuccess(true);
            result.setMessage("Create blog successfully: " + blogEntity.getId());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }

    @GetMapping("/detail/{blogId}")
    public DataApiResult getDetailBlog(@PathVariable int blogId) {
        DataApiResult result = new DataApiResult();

        try {
            Blog blog = blogService.findOne(blogId);
            if (blog == null) {
                result.setSuccess(false);
                result.setMessage("Can't find this blog");
            } else {
                BlogDTO dto = new BlogDTO();
                dto.setId(blog.getId());
                dto.setTitle(blog.getTitle());
                dto.setSlug(blog.getSlug());
                dto.setShortDesc(blog.getShortDesc());
                dto.setMainImage(blog.getMainImage());
                dto.setContent(blog.getContent());
                dto.setCreatedDate(blog.getCreatedDate());
                if(blog.getUser() != null) {
                    dto.setUserId(blog.getUser().getId());
                }

                result.setSuccess(true);
                result.setMessage("Get detail blog successfully !");
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }

    @PostMapping("/update/{blogId}")
    public BaseApiResult updateBlog(@PathVariable int blogId,
                                    @RequestBody BlogDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            Blog blogEntity = blogService.findOne(blogId);
            blogEntity.setTitle(dto.getTitle());
            blogEntity.setSlug(dto.getSlug());
            blogEntity.setShortDesc(dto.getShortDesc());
            blogEntity.setMainImage(dto.getMainImage());
            blogEntity.setContent(dto.getContent());
            blogEntity.setUser(userService.findOne(dto.getUserId()));
            blogEntity.setCreatedDate(new Date());
            blogService.addNewBlog(blogEntity);

            result.setSuccess(true);
            result.setMessage("Update blog " + blogEntity.getId() + " successfully: ");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }

    @GetMapping(value = "/delete/{blogId}")
    public BaseApiResult deleteBlog (@PathVariable int blogId) {
        BaseApiResult result= new BaseApiResult();

        try {
            Blog blog = blogService.findOne(blogId);
            if (blog == null){
                result.setSuccess(false);
                result.setMessage("Can't find this blog.");
            }
            else {
                blogService.deleteBlog(blogId);
                result.setSuccess(true);
                result.setMessage("Delete blog successfully !");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }
}
