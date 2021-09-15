package com.blog.app.controller;

import com.blog.app.domains.LikeUnlikeDomain;
import com.blog.app.service.LikeUnlikeService;
import java.util.Map;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<?> processLikeUnlike(@RequestBody LikeUnlikeDomain domain) {
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
  public Map<String, Integer> getPostLikeUnlikeCount(@PathVariable("postId") int postId) {
    return this.service.getLikeUnlikeCountOnPost(postId);
  }

}
