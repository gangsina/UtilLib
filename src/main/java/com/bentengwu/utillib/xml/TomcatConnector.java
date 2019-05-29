/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.xml;

import com.bentengwu.utillib.reflection.UtilReflection;

/**
 *
 * @author 伟宏
 */
public class TomcatConnector {

    private String executor;
    private String port;
    private String protocol;
    private String connectionTimeout;
    private String maxKeepAliveRequests;
    private String acceptCount;
    private String URIEncoding;
    private String useBodyEncoding;
    private String redirectPort;

    public String getURIEncoding() {
        return URIEncoding;
    }

    public void setURIEncoding(String URIEncoding) {
        this.URIEncoding = URIEncoding;
    }

    public String getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(String acceptCount) {
        this.acceptCount = acceptCount;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getMaxKeepAliveRequests() {
        return maxKeepAliveRequests;
    }

    public void setMaxKeepAliveRequests(String maxKeepAliveRequests) {
        this.maxKeepAliveRequests = maxKeepAliveRequests;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRedirectPort() {
        return redirectPort;
    }

    public void setRedirectPort(String redirectPort) {
        this.redirectPort = redirectPort;
    }

    public String getUseBodyEncoding() {
        return useBodyEncoding;
    }

    public void setUseBodyEncoding(String useBodyEncoding) {
        this.useBodyEncoding = useBodyEncoding;
    }

    @Override
    public String toString() {
        return UtilReflection.toStringRefl(this);
    }
}
