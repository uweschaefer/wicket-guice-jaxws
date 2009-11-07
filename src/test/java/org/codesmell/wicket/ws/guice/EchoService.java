/**
 * 
 */
package org.codesmell.wicket.ws.guice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author doc
 */
@WicketGuiceManaged
@WebService(name = "echo")
public class EchoService
{
    @Inject
    @Named("test")
    private String prefix = "";

    public EchoService()
    {
    }

    public String echo(@WebParam(name = "message") final String msg)
    {
        return prefix + " " + msg;
    }
}
