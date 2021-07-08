package com.juliar.test;

import com.juliar.web.SimpleHTTPServer;
import junit.framework.TestCase;
import org.junit.Test;

public class TestHTTPServer extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
        SimpleHTTPServer.main();
        assertTrue(true);
    }

    @Test
    public void testServer() throws Exception{
    }
}
