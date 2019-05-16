package application.data.service;

import application.data.model.Blog;
import application.data.repository.BlogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    private static final Logger logger = LogManager.getLogger(BlogService.class);

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    public void addNewListBlogs (List<Blog> blogList) {
        try {
            blogRepository.save(blogList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewBlog (Blog blog) {
        try {
            blogRepository.save(blog);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Blog findOne (int blogId) {
        return blogRepository.findOne(blogId);
    }

    public boolean updateBlog (Blog blog) {
        try {
            blogRepository.save(blog);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteBlog (int blogId) {
        try {
            blogRepository.delete(blogId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Blog> getListAllBlogs() {
        try {
            return blogRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public long getTotalBlogs(){
        return blogRepository.getTotalBlogs();
    }

    public Page<Blog> getListBlogByUserOrTitleContaining(Pageable pageable, Integer userId, String title){
        return blogRepository.getListBlogByUserOrTitleContaining(pageable,userId,title);
    }

    public Blog getBlogDetailBySlug(String slug){
        return blogRepository.getBlogDetailBySlug(slug);
    }

}
