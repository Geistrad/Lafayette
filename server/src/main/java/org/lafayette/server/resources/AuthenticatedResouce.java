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
package org.lafayette.server.resources;

/**
 * XXX Consider package private.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class AuthenticatedResouce extends BaseResource {

    /**
     * Call this method to intercept authentication.
     */
    protected final void authenticate() {
        log.debug("Start authentication");

        if (requestHasAuthenticationHEader()) {
            log.debug("Verify authentication.");
            verifyAuthentiaction();

            if (!isAuthenticated()) {
                log.debug("Not authorized! Send forbidden response.");
                sendForbiddenResponse(); // 403
            }
        } else {
            log.debug("Request doesn't have authentication header! Send authentication response.");
            sendAuthenticationResponse(); // 401
        }
    }

    private boolean requestHasAuthenticationHEader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void verifyAuthentiaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isAuthenticated() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void sendForbiddenResponse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void sendAuthenticationResponse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
