package com.phu.blogapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private String img;

    private String author;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    private int likeCount;

    private int viewCount;

    private List<String> tags;

    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;
}
