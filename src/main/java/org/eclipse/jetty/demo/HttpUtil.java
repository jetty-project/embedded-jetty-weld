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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpUtil
{
    private static final HttpClient client;

    static
    {
        client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    }

    public static void sendGet(URI uri) throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        dumpResponse(response);
    }

    public static void dumpResponse(HttpResponse<String> response)
    {
        System.out.printf("%s %d%n", response.version().name(), response.statusCode());
        response.headers().map()
            .forEach((key, values) ->
                System.out.printf("%s: %s%n", key, String.join(", ", values)));
        System.out.println();
        System.out.println(response.body());
    }
}
