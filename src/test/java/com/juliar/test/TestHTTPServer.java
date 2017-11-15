package com.juliar.test;

import com.juliar.web.SimpleHTTPServer;
import junit.framework.TestCase;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class TestHTTPServer extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
        SimpleHTTPServer.main();
    }

    @Test
    public void testServer() throws Exception{
    }
}
