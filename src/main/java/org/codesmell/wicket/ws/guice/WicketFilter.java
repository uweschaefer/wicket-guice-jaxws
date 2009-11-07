/**
 * 
 */
package org.codesmell.wicket.ws.guice;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.wicket.Application;

/**
 * Use this instead of org.apache.wicket.protocol.http.WicketFilter in order to
 * provide access to the Application during servlet request processing.
 * 
 * @author uwe schaefer (http://codesmell.org)
 */
public class WicketFilter extends org.apache.wicket.protocol.http.WicketFilter
{

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException
    {
        super.doFilter(request, response, new ModifiedChain(chain, getApplication()));
    }

    protected Application getApplication()
    {
        // very very nasty, a getter is going to be provided with 1.4.2 or 1.5
        return (Application) getFieldValue(this, "webApplication");
    }

    private static Object getFieldValue(final Object obj, final String string)
    {
        try
        {
            final Field declaredField = obj.getClass().getSuperclass().getDeclaredField(string);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        }
        catch (final Throwable e)
        {
            throw new RuntimeException(e);
        }
    }
}

class ModifiedChain implements FilterChain
{
    private FilterChain orgChain;
    private final Application application;

    public ModifiedChain(final FilterChain chain, final Application application)
    {
        this.orgChain = chain;
        this.application = application;
    }

    public void doFilter(final ServletRequest request, final ServletResponse response) throws IOException,
            ServletException
    {
        Application.set(this.application);
        try
        {
            this.orgChain.doFilter(request, response);
        }
        finally
        {
            Application.unset();
        }
    }

}