package com.blog.app.domains;

import com.blog.app.exception.BadRequestException;

public class LikeUnlikeDomain {

  private int userId;
  private int postId;
  private int likeUnlike;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getPostId() {
    return postId;
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }

  public int getLikeUnlike() {
    return likeUnlike;
  }

  public void setLikeUnlike(int likeUnlike) {
    this.likeUnlike = likeUnlike;
  }

  public void validate(){
    if(this.userId == 0 || this.userId < 0){
      throw new BadRequestException("LUS001", "Invlaid user id");
    }
    if(this.postId == 0 || this.postId < 0){
      throw new BadRequestException("LUS002", "Invlaid post id");
    }

    if(this.likeUnlike != 1 && this.likeUnlike != -1){
      throw new BadRequestException("LUS003", "Invlaid like unlike count");
    }

  }
}
