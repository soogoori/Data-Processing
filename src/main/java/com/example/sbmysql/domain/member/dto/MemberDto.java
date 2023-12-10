package com.example.sbmysql.domain.member.dto;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String nickname,
        String email,
        LocalDate birthday){

}
