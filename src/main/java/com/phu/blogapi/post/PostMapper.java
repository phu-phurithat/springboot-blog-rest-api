package com.phu.blogapi.post;

import com.phu.blogapi.post.Post;
import com.phu.blogapi.post.PostRequest;
import com.phu.blogapi.post.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostMapper {

    public PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .publishDate(post.getPublishDate() != null ? post.getPublishDate().toString() : null)
                .img(post.getImg())
                .tags(post.getTags())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .build();
    }

    public Post toPost(PostRequest postRequest) {
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .img(postRequest.getImg())
                .tags(postRequest.getTags())
                .build();
    }
}
