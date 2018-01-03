package com.juliar.web;


import com.bugsnag.Bugsnag;
import com.juliar.Juliar;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleHTTPServer {

    static private HttpServer server;

    static private Juliar compiler = new Juliar();

    static File jarPath=new File(Juliar.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    static String fullPath = jarPath.getParentFile().getAbsolutePath().replace("\\", "/");

    public static void main()  {
        try {
            server = HttpServer.create(new InetSocketAddress(48042), 0);
            server.createContext("/", new MyHandler());
            server.createContext("/get", new GetHandler());
            server.createContext("/exit", new ExitHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch(Exception e){
            Bugsnag bugsnag = new Bugsnag("c7e03c1e69143ad2fb1f3ea13ed8fda0");
            bugsnag.notify(e);
            System.out.println("Error occured");
        }
    }


    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            String uri = httpExchange.getRequestURI().toString();
            if(uri.isEmpty() || "/".equals(uri)){
                uri = "index.html";
            }
            //remove "/" exploit
            uri = uri.startsWith("/") ? uri.substring(1) : uri;

            if(Juliar.class.getResourceAsStream(uri) != null){
                response = new Scanner(Juliar.class.getResourceAsStream(uri), "UTF-8").useDelimiter("\\A").next();
            } else if(new File(fullPath+uri).exists()){
                response = new String(Files.readAllBytes(Paths.get(fullPath+uri)));
            }

            SimpleHTTPServer.writeResponse(httpExchange, response);
        }
    }

    static class GetHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            StringBuilder response = new StringBuilder();
            String q = httpExchange.getRequestURI().getQuery();

            response.append("<html><body>");
            //Map <String,String> parms = SimpleHTTPServer.queryToMap(httpExchange.getRequestURI().getQuery());
            if(!q.isEmpty() && q.startsWith("q=")){
                response.append(doInPlaceInterpret(q.substring(2)));
            }
            else{
                response.append("File not found");
            }

            response.append("</body></html>");
            SimpleHTTPServer.writeResponse(httpExchange, response.toString());
        }
    }

    static class ExitHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            server.stop(0);
        }
    }


    public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    static private String doInPlaceInterpret( String theCode) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);

        try{
            compiler.isDebug = false;
            InputStream inputStream = new ByteArrayInputStream(theCode.getBytes("UTF-8"));

            List<String> errors = compiler.compile(inputStream, ".", false);

            if (errors != null || errors.size() > 0) {
                for (int i = 0; i < errors.size(); i++) {
                    System.out.println(errors.get(i));
                }
            }

        }
        catch (Exception ex){
            throw ex;
        }
        // Put things back
        System.out.flush();
        System.setOut(old);
        return baos.toString();
    }
}
