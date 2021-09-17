package com.blog.app.service.impl;

import com.blog.app.config.CurrentlyLoggedInUser;
import com.blog.app.domains.PostDomain;
import com.blog.app.entity.PostEntity;
import com.blog.app.exception.PostNotFoundException;
import com.blog.app.repo.PostRepository;
import com.blog.app.repo.UserRepository;
import com.blog.app.service.PostService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final ModelMapper mapper;
  private final UserRepository userRepository;

  public PostServiceImpl(PostRepository postRepository, ModelMapper mapper,
      UserRepository userRepository) {
    this.postRepository = postRepository;
    this.mapper = mapper;
    this.userRepository = userRepository;
  }

  @Override
  public PostDomain add(PostDomain domain) {

    PostEntity entity = mapper.map(domain, PostEntity.class);

    CurrentlyLoggedInUser loggedInUser = (CurrentlyLoggedInUser) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    entity.setUser(this.userRepository.getById(loggedInUser.getUserId()));
    entity = this.postRepository.save(entity);
    return mapper.map(entity, PostDomain.class);
  }

  @Override
  public void update(PostDomain domain) {
    PostEntity fetchedEntity = this.postRepository.getById(domain.getId());
    fetchedEntity.setTitle(domain.getTitle());
    fetchedEntity.setDescription(domain.getDescription());
    this.postRepository.save(fetchedEntity);
  }

  @Override
  public PostDomain getById(int id) {
    Optional<PostEntity> optionalEntity = this.postRepository.findByIdAndDeletedFalse(id);
    if (optionalEntity.isEmpty()) {
      throw new PostNotFoundException("PS001", "Post not found");
    }

    PostEntity entity = optionalEntity.get();
    return this.mapper.map(entity, PostDomain.class);
  }

  @Override
  public List<PostDomain> findAll() {
    List<PostEntity> postList = this.postRepository.findAllPosts();

    if (postList.isEmpty()) {
      return Collections.emptyList();
    }

    List<PostDomain> domain = new ArrayList<>();
    for (PostEntity entity : postList) {
      PostDomain d = new PostDomain();
      d.setTitle(entity.getTitle());
      d.setDescription(entity.getDescription());
      d.setId(entity.getId());
      d.setUserId(entity.getUser().getId());
      d.setEmail(entity.getUser().getEmail());
      d.setCreatedOn(entity.getCreatedOn());
      d.setDeleted(entity.isDeleted());
      d.setDeletedOn(entity.getDeletedOn());
      d.setUpdatedOn(entity.getUpdatedOn());
      domain.add(d);
    }

    return domain;
  }

  @Override
  public void delete(int id) {
    PostEntity fetchedEntity = this.postRepository.getById(id);
    fetchedEntity.setDeleted(true);
    fetchedEntity.setDeletedOn(new Date());
    this.postRepository.save(fetchedEntity);
  }

}
