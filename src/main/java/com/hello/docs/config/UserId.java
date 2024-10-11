package com.hello.docs.config;

import io.swagger.v3.oas.annotations.Hidden;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Hidden // @Hidden 을 사용하지 않으면 Swagger 문서에서 그대로 노출될 수 있습니다. 매번 Controller 에서 애노테이션을 추가하는 것은 번거로우니 여기에 달면 편합니다.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
