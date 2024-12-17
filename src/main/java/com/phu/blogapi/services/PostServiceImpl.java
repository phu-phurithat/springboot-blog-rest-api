package com.phu.blogapi.services;

import com.phu.blogapi.entity.Post;
import com.phu.blogapi.repository.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Override
    public Post createPost(Post post) {
        post.setId(null);
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setPublishDate(new Date());
        return postRepo.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        Optional<Post> optonalPost = postRepo.findById(id);
        if (optonalPost.isPresent()) {
            Post post = optonalPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postRepo.save(post);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @Override
    public Post likePost(Long id) {
        Optional<Post> optonalPost = postRepo.findById(id);
        if (optonalPost.isPresent()) {
            Post post = optonalPost.get();
            post.setLikeCount(post.getLikeCount() + 1);
            return postRepo.save(post);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @Override
    public Post updatePost(Post post) {
        Optional<Post> optonalPost = postRepo.findById(post.getId());
        if (optonalPost.isPresent()) {
            Post updatedPost = optonalPost.get();
            updatedPost.setTitle(post.getTitle());
            updatedPost.setContent(post.getContent());
            updatedPost.setImg(post.getImg());
            updatedPost.setTags(post.getTags());
            updatedPost.setAuthor(post.getAuthor());
            updatedPost.setLastUpdateDate(new Date());
            return postRepo.save(updatedPost);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @Override
    public List<Post> searchByTitle(String title) {
        return postRepo.findAllByTitleContaining(title);
    }

    @Override
    public String deletePost(Long id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if (optionalPost.isPresent()) {
            postRepo.deleteById(id);
            return "Post " + id + " deleted successfully";
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @Override
    public List<Post> getPostsByDateRange(Date start, Date end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return postRepo.findAllByPublishDateBetween(start, end);
    }

    @Override
    public List<Post> getPostsAfter(Date date) {
        return postRepo.findAllByPublishDateAfter(date);
    }
}
