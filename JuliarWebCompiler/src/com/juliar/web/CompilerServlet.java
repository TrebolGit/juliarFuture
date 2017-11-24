package com.juliar.web;

import com.juliar.JuliarCompiler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;



@WebServlet(name = "CompilerServlet", urlPatterns = {"/compile", "/servlets/compiler"})
public class CompilerServlet extends HttpServlet {
    private String loadedComponent;
    private JuliarCompiler compiler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        compiler = new JuliarCompiler();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ( request.getQueryString() != null ){
            try {
                String theCode = request.getParameter("codeArea");

                if ( theCode != null) {
                    doInPlaceInterpret( response, theCode);
                    return;
                }

                /*
                 http://localhost:8080/compile?module=test.jrl&queryFunction=foo
                 */
                String module = request.getParameter("module");
                String queryFunction = request.getParameter("queryfunction");
                if ( queryFunction != null ){
                    response.getOutputStream().print( queryFunction(module, queryFunction) );
                }
            }
            catch (Exception ex){
                response.getOutputStream().println();
                response.getOutputStream().println( ex.getMessage() );
            }

        }
    }

    private void doInPlaceInterpret( HttpServletResponse response, String theCode) throws ServletException, IOException {
        try{
        compiler.isDebug = true;
        InputStream inputStream = new ByteArrayInputStream(theCode.getBytes("UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        System.setOut(new PrintStream(response.getOutputStream()));
        List<String> errors = compiler.compile(inputStream, ".", false);

        if (errors != null || errors.size() > 0) {
            for (int i = 0; i < errors.size(); i++) {
                outputStream.println(errors.get(i));
            }
        }
        outputStream.flush();
        }
        catch (Exception ex){
            throw ex;
        }
    }

    private boolean queryFunction( String module, String function )throws ServletException, IOException {
        try {
            loadJuliarComponentForExecution( module );
            return compiler.queryFunction( function );
        }
        catch ( Exception ex){
            throw ex;
        }
    }


    private void loadJuliarComponentForExecution( String module ) throws ServletException, IOException {
        try {
            loadedComponent = module;
            String moduleLocation = System.getenv("CATALINA_HOME") + File.separator + "moduleLocation" + File.separator + loadedComponent;

            FileInputStream inputStream = new FileInputStream(moduleLocation);

            compiler.isDebug = true;
            compiler.compile(inputStream, ".", false);
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
