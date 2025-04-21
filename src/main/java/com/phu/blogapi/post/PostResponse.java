package com.phu.blogapi.post;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private String publishDate;
    private String img;
    private List<String> tags;
    private int likeCount;
    private int viewCount;
}
