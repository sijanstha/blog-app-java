package com.blog.app.repo;

import com.blog.app.entity.LikeUnlikeEntity;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeUnlikeRepository extends CrudRepository<LikeUnlikeEntity, Integer> {

}
