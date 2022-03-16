package com.example.club_project.service.post;

import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.domain.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostDTO.Response getPostDto(Long postId);

    List<PostDTO.Response> getPostDtos(Long userId, Long clubId, Pageable pageable);

    List<PostDTO.Response> getMyPosts(Long userId, Pageable pageable);

    Long register(Long userId, Long clubId, String title, String content);

    Post getPost(Long postId);

//    List<Post> getPosts(Long userId, Long clubId, Pageable pageable);

    Long update(Long userId, Long postId, String title, String content);

    void delete(Long userId, Long clubId, Long postId);

    void deleteWhenLeaveClub(Long userId, Long clubId);

    boolean isExists(Long postId);
}