package com.xeoscript.libs.integrationsproxy.components;

public interface ResponseValidator<Response> {
    boolean isValid(Response response);
}
