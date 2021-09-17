package com.blog.app.repo;

import com.blog.app.entity.LikeUnlikeEntity;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeUnlikeRepository extends CrudRepository<LikeUnlikeEntity, Integer> {

  LikeUnlikeEntity findByPostIdAndUserIdAndDeletedFalse(int postId, int userId);

  @Query(value = "select l FROM LikeUnlikeEntity l where l.postId = ?1 and l.userId=?2 and l.deleted=false")
  LikeUnlikeEntity fetchUserInteractionOnPost(int postid, int userid);

  @Query(value = "select count(id)\n"
      + "from tbl_like_unlike\n"
      + "where like_unlike = 1\n"
      + "group by fk_post_id\n"
      + "having fk_post_id = ?1", nativeQuery = true)
  Integer getLikeCountOfPost(int postId);

  @Query(value = "select count(id)\n"
      + "from tbl_like_unlike\n"
      + "where like_unlike = -1\n"
      + "group by fk_post_id\n"
      + "having fk_post_id = ?1", nativeQuery = true)
  Integer getUnLikeCountOfPost(int postId);

  Boolean existsByPostIdAndUserIdAndDeletedFalse(int postId, int userId);

}
