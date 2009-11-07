package org.codesmell.wicket.ws.guice;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sun.xml.ws.api.server.InstanceResolverAnnotation;

@Retention(RUNTIME)
@Target(TYPE)
@Documented
@InstanceResolverAnnotation(WicketGuiceResolver.class)
/**
 * Annotate your WebServices with this one in order to use Guice-injected Instances.
 * @author uwe schaefer (http://codesmell.org)
 */
public @interface WicketGuiceManaged {

}
