package com.xeoscript.libs.integrationsproxy.components;

import org.springframework.web.context.request.WebRequest;

/**
 * The responsibility of the class is to extract request details from the incoming web request
 * In case of any encryption, signature verification etc is to be handled then, it has to be
 * done in this class.
 * @param <Request>
 */
public interface RequestParser<Request> {

    public abstract Request parse(WebRequest webRequest);

}
