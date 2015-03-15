/**
 *
 */
package org.codesmell.wicket.ws.guice;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.wicket.Application;
import org.apache.wicket.ThreadContext;

/**
 * Use this instead of org.apache.wicket.protocol.http.WicketFilter in order to
 * provide access to the Application during servlet request processing.
 *
 * @author uwe schaefer (http://codesmell.org)
 */
public class WicketFilter extends org.apache.wicket.protocol.http.WicketFilter {

	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, new ModifiedChain(chain,
				getApplication()));
	}

}

class ModifiedChain implements FilterChain {
	private FilterChain orgChain;
	private final Application application;

	public ModifiedChain(final FilterChain chain, final Application application) {
		this.orgChain = chain;
		this.application = application;
	}

	public void doFilter(final ServletRequest request,
			final ServletResponse response) throws IOException,
			ServletException {
		ThreadContext.get(true);
		ThreadContext.setApplication(this.application);
		try {
			this.orgChain.doFilter(request, response);
		} finally {
			if (ThreadContext.exists()) {
				ThreadContext.detach();
			}
		}
	}

}