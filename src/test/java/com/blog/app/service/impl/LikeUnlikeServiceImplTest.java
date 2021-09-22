package com.blog.app.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.blog.app.domains.LikeUnlikeDomain;
import com.blog.app.entity.UserEntity;
import com.blog.app.exception.BadRequestException;
import com.blog.app.exception.PostNotFoundException;
import com.blog.app.exception.UserNotFoundException;
import com.blog.app.repo.LikeUnlikeRepository;
import com.blog.app.repo.PostRepository;
import com.blog.app.repo.UserRepository;
import com.blog.app.service.LikeUnlikeService;
import com.blog.app.service.PostService;
import com.blog.app.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class LikeUnlikeServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PostRepository postRepository;

  @Mock
  private LikeUnlikeRepository likeUnlikeRepository;

  private LikeUnlikeService underTest;

  @BeforeEach
  void setup() {
    PostService postService = new PostServiceImpl(
        this.postRepository, new ModelMapper(), this.userRepository
    );
    UserService userService = new UserServiceImpl(
        this.userRepository, new ModelMapper(), null
    );

    underTest = new LikeUnlikeServiceImpl(
        this.likeUnlikeRepository, postService, userService
    );
  }

  @Test
  void shouldThrowWhenUserIdIsInvalid() {
    // input/ given
    LikeUnlikeDomain domain = new LikeUnlikeDomain();
    domain.setUserId(-1);
    domain.setPostId(101);
    domain.setLikeUnlike(1);

    // check
    assertThatThrownBy(() -> underTest.performLikeUnlikeOnPost(domain))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Invlaid user id");
  }

  @Test
  void shouldThrowWhenUserNotFound() {
    // input/ given
    LikeUnlikeDomain domain = new LikeUnlikeDomain();
    domain.setUserId(201);
    domain.setPostId(101);
    domain.setLikeUnlike(1);

    assertThatThrownBy(() -> underTest.performLikeUnlikeOnPost(domain))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("User not found");
  }

  @Test
  void shouldThrowWhenPostNotFound() {
    // input/ given
    LikeUnlikeDomain domain = new LikeUnlikeDomain();
    domain.setUserId(201);
    domain.setPostId(101);
    domain.setLikeUnlike(1);

    given(this.userRepository.findById(domain.getUserId()))
        .willReturn(Optional.of(new UserEntity()));
    assertThatThrownBy(() -> underTest.performLikeUnlikeOnPost(domain))
        .isInstanceOf(PostNotFoundException.class)
        .hasMessageContaining("Post not found");
  }
}