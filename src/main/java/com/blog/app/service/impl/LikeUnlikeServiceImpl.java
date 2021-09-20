package com.blog.app.service.impl;

import com.blog.app.domains.LikeUnlikeDomain;
import com.blog.app.entity.LikeUnlikeEntity;
import com.blog.app.repo.LikeUnlikeRepository;
import com.blog.app.service.LikeUnlikeService;
import com.blog.app.service.PostService;
import com.blog.app.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeUnlikeServiceImpl implements LikeUnlikeService {

  private final LikeUnlikeRepository likeUnlikeRepository;
  private final PostService postService;
  private final UserService userService;

  @Autowired
  public LikeUnlikeServiceImpl(LikeUnlikeRepository likeUnlikeRepository,
      PostService postService, UserService userService) {
    this.likeUnlikeRepository = likeUnlikeRepository;
    this.postService = postService;
    this.userService = userService;
  }

  @Override
  public void performLikeUnlikeOnPost(LikeUnlikeDomain domain) {
    // validating domain
    domain.validate();

    this.userService.getById(domain.getUserId());
    this.postService.getById(domain.getPostId());

    LikeUnlikeEntity entity = this.likeUnlikeRepository
        .findByPostIdAndUserIdAndDeletedFalse(domain.getPostId(), domain.getUserId());

    if (entity == null) {
      entity = new LikeUnlikeEntity();
      entity.setPostId(domain.getPostId());
      entity.setUserId(domain.getUserId());
      entity.setLikeUnlike(domain.getLikeUnlike());
    } else {
      entity.setLikeUnlike(domain.getLikeUnlike());
    }

    this.likeUnlikeRepository.save(entity);
  }

  @Override
  public Integer getPostLikeCount(int postId) {
    this.postService.getById(postId);
    return this.likeUnlikeRepository.getLikeCountOfPost(postId);
  }

  @Override
  public Integer getPostUnlikeCount(int postId) {
    this.postService.getById(postId);
    return this.likeUnlikeRepository.getUnLikeCountOfPost(postId);
  }

  @Override
  public Map<String, Integer> getLikeUnlikeCountOnPost(int postId) {
    this.postService.getById(postId);
    Integer likeCount = this.getPostLikeCount(postId);
    Integer unlikeCount = this.getPostUnlikeCount(postId);

    Map<String, Integer> map = new HashMap<>();
    map.put("likeCount", likeCount == null ? 0 : likeCount);
    map.put("unlikeCount", unlikeCount == null ? 0 : unlikeCount);
    return map;
  }

  @Override
  public LikeUnlikeDomain getByPostIdAndUserId(int postId, int userId) {
    LikeUnlikeEntity entity = this.likeUnlikeRepository
        .findByPostIdAndUserIdAndDeletedFalse(postId, userId);
    if (entity == null) {
      return null;
    }

    LikeUnlikeDomain domain = new LikeUnlikeDomain();
    domain.setUserId(entity.getUserId());
    domain.setPostId(entity.getPostId());
    domain.setLikeUnlike(entity.getLikeUnlike());
    return domain;
  }
}
