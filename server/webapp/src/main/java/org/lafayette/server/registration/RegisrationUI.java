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

import org.lafayette.server.business.RegistrationService;
import com.vaadin.annotations.Title;
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

    private final Button createAccountButton = new Button("Create Account");
    private final TextField loginNameField = new TextField("Login name");
    private final TextField emailAddressField = new TextField("Email address");
    private final TextField passwordField = new TextField("Password");
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
     * @param request
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

        formLayout.addComponent(loginNameField);
        loginNameField.setRequired(true);
        loginNameField.setRequiredError("The Field may not be empty.");
        formLayout.addComponent(emailAddressField);
        emailAddressField.setRequired(true);
        emailAddressField.setRequiredError("The Field may not be empty.");
        formLayout.addComponent(passwordField);
        passwordField.setRequired(true);
        passwordField.setRequiredError("The Field may not be empty.");
        formLayout.addComponent(createAccountButton);

        mainLayout.addComponent(formLayout);
        mainLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
    }

    private void initCreateAccountButton() {
        createAccountButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                try {
                    registration.registerNewUser(
                            loginNameField.getValue(),
                            passwordField.getValue(),
                            "realm",
                            emailAddressField.getValue());
                } catch (ServiceExcpetion ex) {
                    Notification.show("Error!", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });
    }
}
