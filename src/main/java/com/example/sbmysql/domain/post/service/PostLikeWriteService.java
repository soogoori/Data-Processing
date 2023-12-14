package com.example.sbmysql.domain.post.service;

import com.example.sbmysql.domain.member.dto.MemberDto;
import com.example.sbmysql.domain.post.entity.Post;
import com.example.sbmysql.domain.post.entity.PostLike;
import com.example.sbmysql.domain.post.respository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeWriteService {

    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {

        var postLike = PostLike.builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();

        return postLikeRepository.save(postLike).getPostId();
    }

}
