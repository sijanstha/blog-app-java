package com.blog.app.controller;

import com.blog.app.config.CurrentlyLoggedInUser;
import com.blog.app.domains.LikeUnlikeDomain;
import com.blog.app.service.LikeUnlikeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/like-unlike")
public class LikeUnlikeController {

  @Autowired
  private LikeUnlikeService service;

  @PostMapping
  public ResponseEntity<?> processLikeUnlike(@RequestBody LikeUnlikeDomain domain,
      Authentication authentication) {

    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    CurrentlyLoggedInUser user = (CurrentlyLoggedInUser) authentication.getPrincipal();
    domain.setUserId(user.getUserId());
    this.service.performLikeUnlikeOnPost(domain);
    return ResponseEntity.ok("SUCCESS");
  }

  @GetMapping("/like-count/{postId}")
  public Integer getLikeCount(@PathVariable("postId") int postId) {
    return this.service.getPostLikeCount(postId);
  }

  @GetMapping("/unlike-count/{postId}")
  public Integer getUnLikeCount(@PathVariable("postId") int postId) {
    return this.service.getPostUnlikeCount(postId);
  }

  @GetMapping("/{postId}")
  public Map<String, Integer> getPostLikeUnlikeCount(@PathVariable("postId") int postId,
      Authentication authentication) {
    Map<String, Integer> map = this.service.getLikeUnlikeCountOnPost(postId);
    if (authentication != null) {
      CurrentlyLoggedInUser user = (CurrentlyLoggedInUser) authentication.getPrincipal();
      LikeUnlikeDomain domain = this.service.getByPostIdAndUserId(postId, user.getUserId());
      if (domain != null) {
        map.put("userAction", domain.getLikeUnlike());
      }
    }

    return map;
  }

}
