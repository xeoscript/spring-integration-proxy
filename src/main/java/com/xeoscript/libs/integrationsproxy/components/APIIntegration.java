package com.xeoscript.libs.integrationsproxy.components;

public interface APIIntegration<Request, Response> {
    Response generate(Request request);
}
