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

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.jboss.weld.environment.servlet.EnhancedListener;

/**
 * Initialize Weld when using a {@link ServletContextHandler}
 */
public class WeldInitializer extends AbstractLifeCycle
{
    private final ServletContextHandler contextHandler;

    public WeldInitializer(ServletContextHandler contextHandler)
    {
        this.contextHandler = contextHandler;
    }

    @Override
    protected void doStart() throws Exception
    {
        EnhancedListener enhancedListener = new EnhancedListener();
        enhancedListener.onStartup(null, contextHandler.getServletContext());
        super.doStart();
    }
}
