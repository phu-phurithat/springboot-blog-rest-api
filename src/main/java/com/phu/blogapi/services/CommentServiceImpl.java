package com.phu.blogapi.services;

import com.phu.blogapi.entity.Comment;
import com.phu.blogapi.entity.Post;
import com.phu.blogapi.repository.CommentRepo;
import com.phu.blogapi.repository.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Override
    public Comment createComment(Long postId, String content, String commentedBy) {
        Optional<Post> optionalPost = postRepo.findById(postId);
        if (optionalPost.isPresent()) {
            Comment comment = new Comment();

            comment.setContent(content);
            comment.setCommentedBy(commentedBy);
            comment.setCreatedDate(new Date());
            comment.setPost(optionalPost.get());

            return commentRepo.save(comment);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepo.findByPostId(postId);
    }
}
