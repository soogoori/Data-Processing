package com.example.sbmysql.application.usecase;

import com.example.sbmysql.domain.follow.entity.Follow;
import com.example.sbmysql.domain.follow.service.FollowReadService;
import com.example.sbmysql.domain.post.entity.Post;
import com.example.sbmysql.domain.post.entity.Timeline;
import com.example.sbmysql.domain.post.service.PostReadService;
import com.example.sbmysql.domain.post.service.TimelineReadService;
import com.example.sbmysql.util.CursorRequest;
import com.example.sbmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetTimelinePostUsecase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {

        // memberId로 팔로우 조회 (내가 팔로우한 사람) ➔ 그 결과로 게시물 조회
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {

        // Timeline 조회 ➔ 그 결과로 게시물 조회
        var pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPosts(postIds);

        return new PageCursor(pagedTimelines.nextCursorRequest(), posts);
    }

}
