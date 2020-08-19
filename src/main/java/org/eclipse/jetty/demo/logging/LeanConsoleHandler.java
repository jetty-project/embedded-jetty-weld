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

package org.eclipse.jetty.demo.logging;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.regex.Pattern;

public class LeanConsoleHandler extends Handler
{
    @Override
    public void close() throws SecurityException
    {
        /* nothing to do here */
    }

    @Override
    public void flush()
    {
        /* nothing to do here */
    }

    public synchronized String formatMessage(LogRecord record)
    {
        String msg = getMessage(record);

        try
        {
            Object[] params = record.getParameters();
            if ((params == null) || (params.length == 0))
            {
                return msg;
            }

            if (Pattern.compile("\\{\\d+}").matcher(msg).find())
            {
                return MessageFormat.format(msg, params);
            }

            return msg;
        }
        catch (Exception ex)
        {
            return msg;
        }
    }

    private String getMessage(LogRecord record)
    {
        ResourceBundle bundle = record.getResourceBundle();
        if (bundle != null)
        {
            try
            {
                return bundle.getString(record.getMessage());
            }
            catch (java.util.MissingResourceException ignored)
            {
            }
        }

        return record.getMessage();
    }

    @Override
    public void publish(LogRecord record)
    {
        StringBuilder buf = new StringBuilder();
        buf.append("[").append(record.getLevel().getName()).append("] ");
        buf.append(record.getLoggerName());
        buf.append(" (").append(record.getThreadID()).append(") ");
        buf.append(formatMessage(record));

        System.out.println(buf.toString());
        if (record.getThrown() != null)
        {
            record.getThrown().printStackTrace(System.out);
        }
    }
}
