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
package org.lafayette.server;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.lafayette.server.http.ForbiddenException;
import org.lafayette.server.http.UnauthorizedException;
import org.lafayette.server.http.digest.AuthorizationHeaderParser;
import org.lafayette.server.http.digest.RequestParameters;
import org.lafayette.server.http.digest.ResponseParameters;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;

/**
 * Filters the request/response for authentication.
 *
 * Filter is only applied to resource methods with annotation {@link Authentication}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class AuthenticationResourceFilterFactory implements ResourceFilterFactory {

    @Context
    private SecurityContext security;
    @Context
    private ServletContext servlet;
    @Context
    private HttpHeaders headers;

    @Override
    public List<ResourceFilter> create(final AbstractMethod am) {
        if (am.isAnnotationPresent(Authentication.class)) {
            return Collections.<ResourceFilter>singletonList(new Filter());
        }

        return Collections.emptyList();
    }

    private class Filter implements ResourceFilter, ContainerRequestFilter, ContainerResponseFilter {

        private final String USER_REALM_PW = DigestUtils.md5Hex("root:Secret Area:1234");

        /**
         * Logger facility.
         */
        private final Logger log = Log.getLogger(this);

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
                verifyAuthentiaction();

                if (isAuthenticated()) {
                    createAndSetPrincipal();
                    return request;
                } else {
                    log.debug("Not authorized! Send forbidden response.");
                    sendForbiddenResponse(); // throws 403
                    return null;
                }
            } else {
                log.debug("Request doesn't have authentication header! Send authentication response.");
                sendAuthenticationResponse(); // throws 401
                return null;
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

        private void verifyAuthentiaction() {
            final String header = getAuthorizationHeader();
            final RequestParameters params = AuthorizationHeaderParser.parseDigestHeaderValue(header);
        }

        private boolean isAuthenticated() {
            return false;
        }

        private void sendForbiddenResponse() {
            throw new ForbiddenException();
        }

        private void sendAuthenticationResponse() {
            final ResponseParameters params = new ResponseParameters();
            params.setRealm(registry().getServerConfig().getSecurotyRealm());
            params.setNonce(registry().getNongeGenerator().getNext());
            throw new UnauthorizedException(params);
        }

        private void createAndSetPrincipal() {
            // TODO
        }

    }
}
