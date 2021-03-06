package com.blog.app.service;

import com.blog.app.domains.LikeUnlikeDomain;
import java.util.Map;

public interface LikeUnlikeService {

  void performLikeUnlikeOnPost(LikeUnlikeDomain domain);

  Integer getPostLikeCount(int postId);

  Integer getPostUnlikeCount(int postId);

  Map<String, Integer> getLikeUnlikeCountOnPost(int postId);

  boolean checkIfUserActionExistsOnSpecPost(int postId, int userId);

  LikeUnlikeDomain getByPostIdAndUserId(int postId, int userId);
}
