package com.example.sbmysql.util;

public record CursorRequest(Long key, int size) {

    public static final Long NONE_KEY = -1L;

    public Boolean hasKey(){
        return key != null;
    }

    // 그 다음 사용할 키
    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
