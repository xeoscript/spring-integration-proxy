package com.xeoscript.libs.integrationsproxy.components;

public interface ResponseSerializer<Response> {
    String serialize(Response response);
}
