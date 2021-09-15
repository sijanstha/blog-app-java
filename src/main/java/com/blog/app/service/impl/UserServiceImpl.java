package com.blog.app.service.impl;

import com.blog.app.domains.UserDomain;
import com.blog.app.entity.UserEntity;
import com.blog.app.exception.UserNotFoundException;
import com.blog.app.repo.UserRepository;
import com.blog.app.service.UserService;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Override
  public UserDomain add(UserDomain domain) {
    domain.setPassword(encoder.encode(domain.getPassword()));
    UserEntity entity = this.userRepository.save(this.mapper.map(domain, UserEntity.class));
    return this.mapper.map(entity, UserDomain.class);
  }

  @Override
  public UserDomain getByEmail(String email) {
    UserEntity entity = this.userRepository.findByEmail(email);
    if (entity == null) {
      return null;
    }
    return this.mapper.map(entity, UserDomain.class);
  }

  @Override
  public UserDomain getById(int userId) {
    Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
    if (!optionalUserEntity.isPresent()) {
      throw new UserNotFoundException("US001", "User not found");
    }
    return this.mapper.map(optionalUserEntity.get(), UserDomain.class);
  }

}
