package com.phu.blogapi.services;

import com.phu.blogapi.entity.Post;

import java.util.Date;
import java.util.List;

public interface PostService {

    Post createPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post updatePost(Post post);

    Post likePost(Long id);

    List<Post> searchByTitle(String title);

    String deletePost(Long id);

    List<Post> getPostsByDateRange(Date start, Date end);

    List<Post> getPostsAfter(Date date);
}
