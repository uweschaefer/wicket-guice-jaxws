package org.codesmell.wicket.ws.guice;

import com.google.inject.Injector;
import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.WSWebServiceContext;
import com.sun.xml.ws.server.AbstractMultiInstanceResolver;

/**
 * Resolves instances of your WebServices from Guice. Needs GuiceWSModule to be
 * installed.
 * 
 * @see GuiceWSModule
 * @author uwe schaefer (http://codesmell.org)
 */
public class WicketGuiceResolver<T> extends AbstractMultiInstanceResolver<T>
{
    private WSWebServiceContext webServiceContext;
    private WSEndpoint<?> endpoint;

    public WicketGuiceResolver(@NotNull final Class<T> clazz) throws IllegalAccessException, InstantiationException
    {
        super(clazz);
    }

    @Override
    public T resolve(@NotNull final Packet packet)
    {
        final T instance = getInjector().getInstance(this.clazz);
        getResourceInjector(this.endpoint).inject(this.webServiceContext, instance);
        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(final WSWebServiceContext wsc, final WSEndpoint endpoint)
    {
        super.start(wsc, endpoint);
        this.endpoint = endpoint;
        this.webServiceContext = wsc;
    }

    private Injector getInjector()
    {
        return WicketGuiceWSBridge.getInjector();
    }
}
