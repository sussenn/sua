package com.itc.sua.common.config;

import com.itc.sua.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionConfig
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
@Configuration
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionConfig extends GlobalExceptionHandler {

}
