/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import org.apache.commons.codec.digest.DigestUtils;
import org.lafayette.server.Registry;
import org.lafayette.server.ServerContextListener;
import org.lafayette.server.domain.User;
import org.lafayette.server.http.ForbiddenException;
import org.lafayette.server.http.UnauthorizedException;
import org.lafayette.server.http.digest.AuthorizationHeaderParser;
import org.lafayette.server.http.digest.Digest;
import org.lafayette.server.http.digest.RequestParameters;
import org.lafayette.server.http.digest.ResponseParameters;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Provider
public class SecurityContextFilterDigest implements SecuirityContextFilter {

    /**
     * Logger facility.
     */
    private final Logger log = Log.getLogger(this);
    private final ServletContext servlet;
    private final HttpHeaders headers;

    public SecurityContextFilterDigest(final ServletContext servlet, final HttpHeaders headers) {
        super();
        this.servlet = servlet;
        this.headers = headers;
    }

    /**
     * Get global registry from servlet context.
     *
     * @return always same instance
     */
    private Registry registry() {
        return (Registry) servlet.getAttribute(ServerContextListener.REGISRTY);
    }

    @Override
    public ContainerRequestFilter getRequestFilter() {
        return this;
    }

    @Override
    public ContainerResponseFilter getResponseFilter() {
        return this;
    }

    @Override
    public ContainerRequest filter(final ContainerRequest request) {
        log.debug("Start authentication");

        if (requestHasAuthenticationHeader()) {
            log.debug("Verify authentication.");

            if (verifyAuthentiaction()) {
                createAndSetPrincipal(request);
                return request;
            } else {
                log.debug("Not authorized! Send forbidden response.");
                throw new ForbiddenException();
            }
        } else {
            log.debug("Request doesn't have authentication header! Send authentication response.");
            final ResponseParameters params = new ResponseParameters();
            params.setRealm(registry().getServerConfig().getSecurotyRealm());
            params.setNonce(registry().getNongeGenerator().getNext());
            throw new UnauthorizedException(params);
        }
    }

    @Override
    public ContainerResponse filter(final ContainerRequest request, final ContainerResponse response) {
        return response;
    }

    private String getAuthorizationHeader() {
        final List<String> authHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty()) {
            log.debug("No authorization header sent by client.");
            return "";
        }

        if (authHeader.size() > 1) {
            log.warn("More than one authorization header sent by client! Using first one and ignore others.");
        }

        return authHeader.get(0);
    }

    private boolean requestHasAuthenticationHeader() {
        return !getAuthorizationHeader().isEmpty();
    }

    private boolean verifyAuthentiaction() {
        final String header = getAuthorizationHeader();
        final RequestParameters params = AuthorizationHeaderParser.parseDigestHeaderValue(header);
        final String userRealmPw = DigestUtils.md5Hex(String.format("Foo:%s:1234",
                registry().getServerConfig().getSecurotyRealm()));
        // TODO Verify nonce
        final String expectedResponse = Digest.digest(userRealmPw,
                params.getNonce(),
                Digest.digestRequestData(params.getHttpMethod(), params.getRequestedUri()));
        return expectedResponse.equalsIgnoreCase(params.getResponse());
    }

    private void createAndSetPrincipal(final ContainerRequest request) {
        final User principal = new User(23, "Snafu", "foo", "bar");
        request.setSecurityContext(new SecurityContextImpl(principal));
    }
}
