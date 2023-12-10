package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id){

        var member = memberRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        var members = memberRepository.findAllByIdIn(ids);
        return members.stream().map(this::toDto).toList();
    }
    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository
                .findAllByMemberId(memberId)
                .stream()
                .map(history -> toDto(history))
                .toList();
    }
    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getNickname(), member.getEmail(), member.getBirthday());
    }

    public MemberNicknameHistoryDto toDto(MemberNicknameHistory memberNicknameHistory) {
        return new MemberNicknameHistoryDto(
                memberNicknameHistory.getId(),
                memberNicknameHistory.getMemberId(),
                memberNicknameHistory.getNickname(),
                memberNicknameHistory.getCreatedAt()
        );
    }
}
