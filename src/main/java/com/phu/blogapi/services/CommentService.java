package com.phu.blogapi.services;

import com.phu.blogapi.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long postId, String content, String commentedBy);
    List<Comment> getCommentsByPostId(Long postId);
}
