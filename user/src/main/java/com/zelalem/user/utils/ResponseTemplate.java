package com.zelalem.user.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate <T> {
    private Integer code;
    private String message;
    private T data;
}
