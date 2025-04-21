package com.phu.blogapi.post;

import com.phu.blogapi.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService{

    private final PostRepo postRepo;
    private final PostMapper postMapper;

    public PostResponse createPost(PostRequest request) {
         return postMapper.toPostResponse(
                 postRepo.save(
                         postMapper.toPost(request)));
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    public PostResponse getPostById(Long id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postMapper.toPostResponse(postRepo.save(post));
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }

    public void likePost(Long id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }

    public PostResponse updatePost(PostRequest request, Long id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setViewCount(post.getViewCount());
            post.setLikeCount(post.getLikeCount());
            return postMapper.toPostResponse(postRepo.save(post));
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }

    public List<PostResponse> searchByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        List<Post> result = postRepo.findAllByTitleContaining(title);
        if (result.isEmpty()) {
            throw new PostNotFoundException("No posts found with title: " + title);
        }
        return result.stream()
                .map(postMapper::toPostResponse)
                .toList();
    }

    public void deletePost(Long id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if (optionalPost.isPresent()) {
            postRepo.delete(optionalPost.get());
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }

//    public List<PostResponse> getPostsByDateRange(String start, String end) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            startDate = format.parse(start);
//            endDate = format.parse(end);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.");
//        }
//        return postRepo.findAllByPublishDateBetween(startDate, endDate)
//                .stream().map(postMapper::toPostResponse).toList();
//    }
}
