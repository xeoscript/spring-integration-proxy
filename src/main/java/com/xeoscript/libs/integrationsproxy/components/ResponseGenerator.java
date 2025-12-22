package com.xeoscript.libs.integrationsproxy.components;

public interface ResponseGenerator<APIResponse, Response> {
    Response serialize(APIResponse apiResponse);
}
