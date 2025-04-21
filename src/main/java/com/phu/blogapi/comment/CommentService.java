package com.phu.blogapi.comment;

import com.phu.blogapi.post.Post;
import com.phu.blogapi.post.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;

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
