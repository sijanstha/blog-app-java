package com.blog.app.controller;

import com.blog.app.domains.CommentDomain;
import com.blog.app.domains.PostDomain;
import com.blog.app.exception.PostNotFoundException;
import com.blog.app.service.CommentService;
import com.blog.app.service.PostService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

  @GetMapping("/create-post")
  public String showCreatePostForm(Model model) {
    model.addAttribute("postDomain", new PostDomain());
    return "create-post";
  }

  @PostMapping("/create-post")
  public String processCreatePost(@ModelAttribute @Valid PostDomain postDomain,
      BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(
          err -> System.out.println(err)
      );
      return "create-post";
    }

    if (postDomain.getId() > 0) {
      this.postService.update(postDomain);
    } else {
      this.postService.add(postDomain);
    }
    return "redirect:/";
  }

  @GetMapping("/{id}/edit")
  public String showEditPostPage(@PathVariable("id") int id, Model model) {
    model.addAttribute("postDomain", this.postService.getById(id));
    return "create-post";
  }

  @GetMapping("/{id}/details")
  public String showPostDetailsPage(@PathVariable("id") int id, Model model) {
    model.addAttribute("postDetails", this.postService.getById(id));
    model.addAttribute("commentDomain", new CommentDomain());
    model.addAttribute("comments", this.commentService.findAllByPostId(id));
    return "post-details";
  }

  @GetMapping("/{id}/delete")
  public String handleDeletePost(@PathVariable("id") int id) {
    this.postService.delete(id);
    return "redirect:/";
  }

  @ExceptionHandler(PostNotFoundException.class)
  public String getErrorPage(PostNotFoundException exception, Model m) {
    m.addAttribute("message", exception.getMessage());
    return "error";
  }
}
