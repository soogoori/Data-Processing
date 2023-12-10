package com.example.sbmysql.domain.post.dto;

import java.time.LocalDate;

public record DailyPostCountResponse(Long memberId, LocalDate date, Long postCount) {
}
