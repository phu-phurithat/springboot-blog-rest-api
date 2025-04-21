package com.phu.blogapi.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findAllByTitleContaining(String title);

    List<Post> findAllByPublishDateBetween(Date start, Date end);

    List<Post> findAllByPublishDateAfter(Date date);
}
