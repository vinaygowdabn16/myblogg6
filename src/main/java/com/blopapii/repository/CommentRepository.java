package com.blopapii.repository;

import com.blopapii.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(long postId);

    List<Comment> findByEmail(String email);

    List<Comment> findByPostIdAndEmail(long postId,String email);
    List<Comment> findByPostIdOrEmail(long postId,String email);

    boolean existsByPostId(long postId);
}
