package org.codesmell.wicket.ws.guice;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see foo.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
    /**
     * Constructor
     */
    public WicketApplication()
    {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<HomePage> getHomePage()
    {
        return HomePage.class;
    }

    @Override
    protected void init()
    {
        final Injector i = Guice.createInjector(new AbstractModule()
        {
            @Override
            public void configure()
            {
                bind(String.class).annotatedWith(Names.named("test")).toInstance("(injected test value)");
                bind(EchoService.class).asEagerSingleton();
            }
        });

        addComponentInstantiationListener(new GuiceComponentInjector(this, i));
        // ...
    }

}
