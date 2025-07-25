package com.example.likesystem.model.enums;

import lombok.Getter;

/**
 * 脚本执行结果
 * @author alanz
 */
@Getter
public enum LuaStatusEnum {
    //成功
    SUCCESS(1L),

    //失败
    FAIL(-1L),
    ;

    private final long value;

    LuaStatusEnum(long value) {
        this.value = value;
    }
}
