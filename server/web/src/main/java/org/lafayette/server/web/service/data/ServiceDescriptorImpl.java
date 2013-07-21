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
package org.lafayette.server.web.service.data;

import com.google.common.collect.Sets;
import java.lang.reflect.Method;
import java.util.Collection;
import org.apache.commons.lang3.Validate;
import org.lafayette.server.web.service.DescribableService;
import org.lafayette.server.web.service.Service;
import org.lafayette.server.web.service.ServiceApi;
import org.lafayette.server.web.service.ServiceDescriptor;

/**
 * Describes services by examine annotations.
 *
 * For the service main description the {@link Service service annotation} is examined.
 * For the API descriptions the {@link ServiceApi API annotation} is examined.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ServiceDescriptorImpl implements ServiceDescriptor {

    /**
     * The service type to describe.
     */
    private final Class<? extends DescribableService> describable;
    /**
     * Lazy computed.
     */
    private String serviceDescription;
    /**
     * Lazy computed.
     */
    private Collection<String> apiDescription;

    /**
     * Dedicated constructor.
     *
     * @param describable described type, must not be {@code null}
     */
    public ServiceDescriptorImpl(final Class<? extends DescribableService> describable) {
        super();
        Validate.notNull(describable);
        this.describable = describable;
    }

    @Override
    public String getServiceDescription() {
        if (null == serviceDescription) {
            final Service service = describable.getAnnotation(Service.class);

            if (service == null) {
                serviceDescription = "";
            } else {
                serviceDescription = service.value();
            }
        }

        return serviceDescription;
    }

    @Override
    public Collection<String> getApiDescription() {
        if (null == apiDescription) {
            apiDescription = Sets.newHashSet();

            for (final Method method : describable.getDeclaredMethods()) {
                final ServiceApi api = method.getAnnotation(ServiceApi.class);

                if (api != null) {
                    apiDescription.add(api.value());
                }
            }
        }

        return apiDescription;
    }

}
