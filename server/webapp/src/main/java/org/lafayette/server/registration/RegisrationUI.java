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
package org.lafayette.server.registration;

import org.apache.commons.validator.routines.EmailValidator;
import org.lafayette.server.business.RegistrationService;
import com.vaadin.annotations.Title;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.lafayette.server.Registry;
import org.lafayette.server.ServerContextListener;
import org.lafayette.server.business.ServiceExcpetion;
import org.lafayette.server.domain.Finders;

/**
 * UI class is the starting point for your app.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Title("Sign up")
public class RegisrationUI extends UI {
    /**
     * Minimum password length.
     */
    private static final int PASSWORD_MIN_LENGTH = 8;
    /**
     * Error message password empty.
     */
    private static final String ERR_MSG_PASSWORD_MUST_NOT_BE_EMPTY =
            "Password must not be empty!";
    /**
     * Error message email empty.
     */
    private static final String ERR_MSG_EMAIL_ADDRESS_MUST_NOT_BE_EMPTY =
            "Email address must not be empty!";
    /**
     * Error message login name empty.
     */
    private static final String ERR_MSG_LOGIN_NAME_MUST_NOT_BE_EMPTY =
            "Login name must not be empty!";
    /**
     * Error message password is same as login name.
     */
    private static final String ERR_MSG_PASSWORD_MUST_NOT_BE_SAME_AS_LOGIN_NAME =
            "Password must not be same as login name!";
    /**
     * Error message password is same as email.
     */
    private static final String ERR_MSG_PASSWORD_MUST_NOT_BE_SAME_AS_EMAIL_ADDRES =
            "Password must not be same as email address!";
    /**
     * Error message password is too short.
     */
    private static final String ERR_MSG_PASSWORD_IS_TOO_SHORT =
            "Password must have at least " + PASSWORD_MIN_LENGTH + " charatcers!";

    /**
     * Button to create account.
     */
    private final Button createAccountButton = new Button("Create Account");
    /**
     * Input field for login name.
     */
    private final TextField loginNameField = new TextField("Login name");
    /**
     * Input field for email.
     */
    private final TextField emailAddressField = new TextField("Email address");
    /**
     * Input field for password.
     */
    private final TextField passwordField = new TextField("Password");
    /**
     * Business layer.
     */
    private RegistrationService registration;

    /**
     * Get object registry for shared objects.
     *
     * @return never {@code null}
     */
    protected Registry registry() {
        return (Registry) VaadinServlet.getCurrent().getServletContext().getAttribute(ServerContextListener.REGISRTY);
    }

    /**
     * After UI class is created, init() is executed.
     *
     * You should build and wire up your user interface here.
     *
     * @param request Vaadin request
     */
    @Override
    protected void init(final VaadinRequest request) {
        registration = new RegistrationService(new Finders(registry().getMappers()));
        initLayout();
        initCreateAccountButton();
    }

    /**
     * Initializes layout and components.
     */
    private void initLayout() {
        final VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        setContent(mainLayout);
        final FormLayout formLayout = new FormLayout();
        formLayout.setSizeUndefined();


        loginNameField.setRequired(true);
        loginNameField.setRequiredError(ERR_MSG_LOGIN_NAME_MUST_NOT_BE_EMPTY);
        formLayout.addComponent(loginNameField);

        emailAddressField.setRequired(true);
        emailAddressField.setRequiredError(ERR_MSG_EMAIL_ADDRESS_MUST_NOT_BE_EMPTY);
        formLayout.addComponent(emailAddressField);

        passwordField.setRequired(true);
        passwordField.setRequiredError(ERR_MSG_PASSWORD_MUST_NOT_BE_EMPTY);
        formLayout.addComponent(passwordField);

        formLayout.addComponent(createAccountButton);

        mainLayout.addComponent(formLayout);
        mainLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
    }

    /**
     * Initializes the "create account" button.
     */
    private void initCreateAccountButton() {
        createAccountButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                boolean isInputFaulty = false;

                final String loginName = loginNameField.getValue();
                final String password = passwordField.getValue();
                final String emailAddress = emailAddressField.getValue();


                try {
                    validatePassword(password, loginName, emailAddress);
                } catch (IllegalArgumentException ex) {
                    passwordField.setComponentError(new UserError(ex.getMessage()));
                    isInputFaulty = true;
                }

                try {
                    validateEmailAddress(emailAddress);
                } catch (IllegalArgumentException ex) {
                    emailAddressField.setComponentError(new UserError(ex.getMessage()));
                    isInputFaulty = true;
                }

                if (isInputFaulty) {
                    return;
                }

                try {
                    registration.registerNewUser(
                            loginName,
                            password,
                            registry().getInitParameters().getRealm(),
                            emailAddress);
                } catch (ServiceExcpetion ex) {
                    Notification.show("Error!", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Validates password.
     *
     * Checks that:
     * <ul>
     * <li>password is not same as login name (case insensitive)
     * <li>password is not same as email address
     * <li>password is not shorter than eight characters
     * </ul>
     *
     * @param password password to check
     * @param loginName log=in name to check against
     * @param emailAddress email address to check against
     */
    static void validatePassword(final String password, final String loginName, final String emailAddress) {
        if (password.equalsIgnoreCase(loginName)) {
            throw new IllegalArgumentException(ERR_MSG_PASSWORD_MUST_NOT_BE_SAME_AS_LOGIN_NAME);
        }

        if (password.equalsIgnoreCase(emailAddress)) {
            throw new IllegalArgumentException(ERR_MSG_PASSWORD_MUST_NOT_BE_SAME_AS_EMAIL_ADDRES);
        }

        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new IllegalArgumentException(ERR_MSG_PASSWORD_IS_TOO_SHORT);
        }
    }

    /**
     * Validates email address against RFC and throws {@link IllegalArgumentException} if invalid.
     *
     * @param emailaddress address to validate
     */
    static void validateEmailAddress(final String emailaddress) {
        if (!EmailValidator.getInstance().isValid(emailaddress)) {
            throw new IllegalArgumentException("Email address is not valid!");
        }
    }
}
