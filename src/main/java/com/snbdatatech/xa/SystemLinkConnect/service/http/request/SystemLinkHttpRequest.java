package com.snbdatatech.xa.SystemLinkConnect.service.http.request;

import java.net.URLConnection;

/**
 * Created by sblin on 7/7/2016.
 */
public interface SystemLinkHttpRequest {
    boolean sendSystemLinkRequest(URLConnection connection);
}
