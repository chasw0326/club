package com.example.club_project.service.post;

import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.domain.Club;
import com.example.club_project.domain.Post;
import com.example.club_project.domain.User;
import com.example.club_project.repository.PostRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ClubService clubService;
    private final ClubJoinStateService clubJoinStateService;

    @Override
    @Transactional(readOnly = true)
    public PostDTO.Response getPostDto(Long postId) {
        Object result = postRepository.getPostById(postId);
        Object[] postData = (Object[]) result;
        Post post = (Post) postData[0];
        User user = (User) postData[1];
        Long commentCount = (Long) postData[2];

        return PostDTO.Response.builder()
                .profileUrl(user.getProfileUrl())
                .nickname(user.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCnt(commentCount)
                .createdAt(post.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO.Response> getPostDtos(Long userId, Long clubId, Pageable pageable) {
        if (!clubJoinStateService.isJoined(userId, clubId)) {
            // TODO: 수정예정
            throw new AccessDeniedException("클럽 멤버가 아닙니다.");
        }
        List<Object[]> posts = postRepository.getPostWithCommentCountByClubId(clubId, pageable);
        return getResponses(posts);
    }

    @Override
    @Transactional
    public Long register(Long userId, Long clubId, String title, String content) {
        if (!clubJoinStateService.isJoined(userId, clubId)) {
            // TODO: 수정예정
            throw new AccessDeniedException("클럽 멤버가 아닙니다.");
        }
        User user = userService.getUser(userId);
        Club club = clubService.getClub(clubId);

        Post post = Post.builder()
                .user(user)
                .club(club)
                .title(title)
                .content(content)
                .build();

        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId).
                orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts(Long userId, Long clubId, Pageable pageable) {
        if (!clubJoinStateService.isJoined(userId, clubId)) {
            // TODO: 예외 뭘로 할지 수정예정
            throw new AccessDeniedException("클럽에 가입한 사람만 볼수 있습니다.");
        }
        return postRepository.findAllByClub_IdOrderByIdDesc(clubId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO.Response> getMyPosts(Long userId, Pageable pageable) {
        List<Object[]> posts = postRepository.getPostWithCommentCountByUserId(userId, pageable);
        return getResponses(posts);
    }

    @Override
    @Transactional
    public Long update(Long userId, Long postId, String title, String content) {

        Post post = postRepository.findById(postId).
                orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));

        if (!post.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("수정할 권한이 없습니다.");
        }

        post.update(title, content);
        return post.getId();
    }


    @Override
    @Transactional
    public void delete(Long userId, Long clubId, Long postId) {
        // TODO: 에러들 나중에 메시지들 혹은 에러타입 다 수정예정
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));

        // 본인이 이거나 매니저면 글 삭제가 가능하다.
        if (post.getUser().getId().equals(userId) || clubJoinStateService.hasManagerRole(userId, clubId)) {
            postRepository.delete(post);
        } else {
            throw new AccessDeniedException("삭제할 권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteWhenLeaveClub(Long userId, Long clubId){
        List<Post> posts = postRepository.findAllByUser_IdAndClub_Id(userId, clubId);
        postRepository.deleteAll(posts);
    }

    @Override
    @Transactional
    public boolean isExists(Long postId) {
        return postRepository.existsById(postId);
    }


    private List<PostDTO.Response> getResponses(List<Object[]> posts) {
        List<PostDTO.Response> postDtos = new ArrayList<>();
        for (Object[] postData : posts) {
            Post post = (Post) postData[0];
            User user = (User) postData[1];
            Long commentCnt = (Long) postData[2];
            postDtos.add(PostDTO.Response.builder()
                    .nickname(user.getNickname())
                    .profileUrl(user.getProfileUrl())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .commentCnt(commentCnt)
                    .build());
        }
        return postDtos;
    }
}
