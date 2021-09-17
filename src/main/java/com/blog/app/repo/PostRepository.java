package com.blog.app.repo;

import com.blog.app.entity.PostEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

  @Query(value = "select * from tbl_posts where is_deleted=false", nativeQuery = true)
  List<PostEntity> findAllPosts();

  Optional<PostEntity> findByIdAndDeletedFalse(Integer integer);
}
