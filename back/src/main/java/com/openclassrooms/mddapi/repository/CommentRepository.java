package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
