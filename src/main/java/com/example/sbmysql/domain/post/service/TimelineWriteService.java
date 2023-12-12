package com.example.sbmysql.domain.post.service;

import com.example.sbmysql.domain.post.entity.Timeline;
import com.example.sbmysql.domain.post.respository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {
    private final TimelineRepository timelineRepository;

    /**
     * 게시물 작성 시 해당 회원을 팔로우한 회원들에게 데이터 전달
     * @param postId
     * @param toMemberIds
     */
    public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
        var timelines = toMemberIds.stream()
                .map(memberId -> toTimeline(postId, memberId))
                .toList();
        timelineRepository.bulkInsert(timelines);
    }

    private static Timeline toTimeline(Long postId, Long memberId) {
        return Timeline.builder().memberId(memberId).postId(postId).build();
    }
}
