//
//  ========================================================================
//  Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.demo;

import org.eclipse.jetty.demo.logging.Logging;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.LifeCycle;

/**
 * Example of older / legacy Weld {@link org.jboss.weld.environment.servlet.Listener} support.
 * <p>
 * This works for Servlets and Servlet Filters, but not Servlet Listeners.
 * </p>
 */
public class EmbeddedListenerDemo
{
    public static void main(String[] args) throws Exception
    {
        Logging.config();
        Server server = new Server(8099);

        final ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        contextHandler.addServlet(HelloServlet.class, "/hi/*");
        contextHandler.addServlet(GreetingsServlet.class, "/greetings/*");
        contextHandler.addEventListener(new org.jboss.weld.environment.servlet.Listener());
        server.setHandler(contextHandler);
        try
        {
            server.start();
            HttpUtil.sendGet(server.getURI().resolve("/hi/"));
            HttpUtil.sendGet(server.getURI().resolve("/greetings/"));
        }
        finally
        {
            LifeCycle.stop(server);
        }
    }
}
