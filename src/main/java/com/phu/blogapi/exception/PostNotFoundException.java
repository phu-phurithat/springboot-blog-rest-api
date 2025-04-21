package com.phu.blogapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostNotFoundException extends RuntimeException {

    private final String message;
}
