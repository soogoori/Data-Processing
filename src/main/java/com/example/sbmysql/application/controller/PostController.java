package com.example.sbmysql.application.controller;

import com.example.sbmysql.application.usecase.CreatePostLikeUsecase;
import com.example.sbmysql.application.usecase.CreatePostUsecase;
import com.example.sbmysql.application.usecase.GetTimelinePostUsecase;
import com.example.sbmysql.domain.post.dto.DailyPostCountRequest;
import com.example.sbmysql.domain.post.dto.DailyPostCountResponse;
import com.example.sbmysql.domain.post.dto.PostCommand;
import com.example.sbmysql.domain.post.dto.PostDto;
import com.example.sbmysql.domain.post.entity.Post;
import com.example.sbmysql.domain.post.service.PostReadService;
import com.example.sbmysql.domain.post.service.PostWriteService;
import com.example.sbmysql.util.CursorRequest;
import com.example.sbmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostUsecase getTimelinePostUsecase;
    private final CreatePostUsecase createPostUsecase;
    private final CreatePostLikeUsecase createPostLikeUsecase;


    @PostMapping
    public Long create(@RequestBody PostCommand command) {
        return createPostUsecase.execute(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCountResponse> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPosts(@PathVariable Long memberId, Pageable pageable) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/bu-cursor")
    public PageCursor<Post> getPostsByCursor(@PathVariable Long memberId, CursorRequest cursorRequest) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimeline(@PathVariable Long memberId, CursorRequest cursorRequest) {
        return getTimelinePostUsecase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like/v1")
    public void likePost(@PathVariable Long postId) {
        //postWriteService.likePost(postId);
        postWriteService.likePostByOptimisticLock(postId);
    }

    @PostMapping("/{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId, @RequestParam Long memberId) {
        createPostLikeUsecase.execute(postId, memberId);

    }

}
