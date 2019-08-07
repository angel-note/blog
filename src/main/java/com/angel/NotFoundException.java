package com.angel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  HttpStatus.NOT_FOUND : 这个是404的状态，只有设置了，才会返回到404页面(根据不同的状态码，SpringBoot会找到对应的页面并显示)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
