package com.example.sbmysql.domain.post.dto;

public record PostCommand(
        Long memberId,
        String contents
) {
}
