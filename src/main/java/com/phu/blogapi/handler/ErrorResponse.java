package com.phu.blogapi.handler;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors
) {

}
