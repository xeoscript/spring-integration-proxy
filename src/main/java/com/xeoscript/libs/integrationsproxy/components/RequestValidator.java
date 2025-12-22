package com.xeoscript.libs.integrationsproxy.components;

public interface RequestValidator<Request> {

    void isValid(Request request);
}
