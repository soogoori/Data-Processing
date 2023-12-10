package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowingMemberUsecase {

    final private MemberReadService memberReadService;
    final private FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followingMembers = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMembers);
    }
}
