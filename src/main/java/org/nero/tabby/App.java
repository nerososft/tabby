package org.nero.tabby;

import org.nero.tabby.http.HttpServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }
}
