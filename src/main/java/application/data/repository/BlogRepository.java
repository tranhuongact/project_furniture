package application.data.repository;

import application.data.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository <Blog, Integer> {

    @Query("select count(p.id) from dbo_post p")
    long getTotalBlogs();

    @Query("SELECT p FROM dbo_post p " +
            "WHERE (:userId IS NULL OR (p.userId = :userId)) " +
            "AND (:title IS NULL OR UPPER(p.title) LIKE CONCAT('%',UPPER(:title),'%'))" +
            "ORDER BY p.createdDate desc")
    Page<Blog> getListBlogByUserOrTitleContaining(Pageable pageable, @Param("userId") Integer userId, @Param("title") String title);

    @Query("SELECT p FROM dbo_post p " +
            "WHERE (:slug IS NULL OR (p.slug = :slug))")
    Blog getBlogDetailBySlug(@Param("slug") String slug);

}