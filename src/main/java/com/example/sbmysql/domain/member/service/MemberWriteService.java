package com.example.sbmysql.domain.member.service;

import com.example.sbmysql.domain.member.dto.RegisterMemberCommand;
import com.example.sbmysql.domain.member.entity.Member;
import com.example.sbmysql.domain.member.entity.MemberNicknameHistory;
import com.example.sbmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.sbmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member create(RegisterMemberCommand command){

        // 회원정보(이메일, 닉네임, 생년월일) 등록
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        var savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(history);
    }

}
