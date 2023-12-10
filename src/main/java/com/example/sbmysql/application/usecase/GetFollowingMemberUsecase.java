package com.example.sbmysql.application.usecase;

import com.example.sbmysql.domain.follow.entity.Follow;
import com.example.sbmysql.domain.follow.service.FollowReadService;
import com.example.sbmysql.domain.member.dto.MemberDto;
import com.example.sbmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowingMemberUsecase {

    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followingMembers = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMembers);
    }
}
