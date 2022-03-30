package com.example.club_project.service.post;

import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.domain.Club;
import com.example.club_project.domain.Post;
import com.example.club_project.domain.User;
import com.example.club_project.exception.custom.ForbiddenException;
import com.example.club_project.exception.custom.InvalidAccessException;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.repository.PostRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import com.example.club_project.service.user.UserService;
import com.example.club_project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ValidateUtil validateUtil;

    @Override
    @Transactional(readOnly = true)
    public PostDTO.Response getPostDto(Long postId) {
        Object result = postRepository.findPostById(postId).
                orElseThrow(() -> new NotFoundException("throw notFoundException"));
        Object[] postData = (Object[]) result;
        Post post = (Post) postData[0];
        User user = (User) postData[1];
        Long commentCount = (Long) postData[2];

        return PostDTO.Response.builder()
                .postId(post.getId())
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
    public List<PostDTO.Response> getClubPosts(Long userId, Long clubId, Pageable pageable) {
        Club club = clubService.getClub(clubId);

        if (!clubJoinStateService.isJoined(userId, clubId)) {
            throw new InvalidAccessException("not a member of the club");
        }
        List<Object[]> posts = postRepository.getPostWithCommentCountByClubId(clubId, pageable);
        return getResponses(posts);
    }

    @Override
    @Transactional
    public Long register(Long userId, Long clubId, String title, String content) {
        User user = userService.getUser(userId);
        Club club = clubService.getClub(clubId);

        if (!clubJoinStateService.isJoined(userId, clubId)) {
            throw new InvalidAccessException("not a member of the club");
        }

        Post post = Post.builder()
                .user(user)
                .club(club)
                .title(title)
                .content(content)
                .build();

        validateUtil.validate(post);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId).
                orElseThrow(() -> new NotFoundException("can not find post by postId: " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO.Response> getMyPosts(Long userId, Pageable pageable) {
        List<Object[]> posts = postRepository.getPostWithCommentCountByUserId(userId, pageable);
        return getResponses(posts);
    }

    @Override
    @Transactional
    public Long update(Long userId, Long clubId, Long postId, String title, String content) {
        Club club = clubService.getClub(clubId);

        if (!clubJoinStateService.isJoined(userId, clubId)) {
            throw new InvalidAccessException("not a member of the club");
        }

        Post post = this.getPost(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("No authority to update");
        }

        post.update(title, content);
        validateUtil.validate(post);
        return post.getId();
    }


    @Override
    @Transactional
    public void delete(Long userId, Long clubId, Long postId) {
        Post post = this.getPost(postId);
        // 본인이 이거나 매니저면 글 삭제가 가능하다.
        if (post.getUser().getId().equals(userId) || clubJoinStateService.hasManagerRole(userId, clubId)) {
            postRepository.delete(post);
        } else {
            throw new ForbiddenException("No authority to delete");
        }
    }

    @Override
    @Transactional
    public void deleteWhenLeaveClub(Long userId, Long clubId) {
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
                    .postId(post.getId())
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