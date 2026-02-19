package com.xeoscript.libs.integrationsproxy.components;

public interface ResponseSerializer<Request, Response> {
    String serialize(Request request, Response response);
}
