package com.blog.app.domains;

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
      // throw exception
    }
    if(this.postId == 0 || this.postId < 0){
      // throw exception
    }

    if(this.likeUnlike != 1 && this.likeUnlike != -1){
      // throw exception
    }

  }
}
