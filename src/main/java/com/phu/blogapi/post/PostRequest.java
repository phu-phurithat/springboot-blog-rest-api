package com.phu.blogapi.post;

import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

    private String title;
    private String content;
    private String author;
    private String img;
    private List<String> tags;
}
