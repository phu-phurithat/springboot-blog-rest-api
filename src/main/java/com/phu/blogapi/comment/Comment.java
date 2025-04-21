package com.phu.blogapi.comment;

import com.phu.blogapi.post.Post;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private String commentedBy;

    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
