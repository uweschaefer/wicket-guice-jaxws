/**
 * 
 */
package org.codesmell.wicket.ws.guice;

import org.apache.wicket.Application;
import org.apache.wicket.guice.GuiceInjectorHolder;

import com.google.inject.Injector;

/**
 * @author uwe schaefer (http://codesmell.org)
 */
class WicketGuiceWSBridge
{
    static Injector getInjector()
    {
        final Application application = Application.get();
        if (application == null)
        {
            throw new IllegalStateException(
                    "Application reference not obtainable. Make sure, you use the WicketSessionFilter wrapped around your WebSerice. (see http://code.google.com/p/wicket-guice-jaxws/wiki/Usage)");
        }

        final GuiceInjectorHolder holder = application.getMetaData(GuiceInjectorHolder.INJECTOR_KEY);
        if (holder == null)
        {
            throw new IllegalStateException(
                    "GuiceInjectorHolder not obtainable from Wicket Application. Does your Wicket Application use Guice? (see http://code.google.com/p/wicket-guice-jaxws/wiki/Usage)");
        }

        return holder.getInjector();
    }

}
