package com.juliar.web;

import com.juliar.JuliarCompiler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;



@WebServlet(name = "CompilerServlet", urlPatterns = {"/compile", "/servlets/compiler"})
public class CompilerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getQueryString() != null){
            try {
                String theCode = request.getParameter("codeArea");

                JuliarCompiler compiler = new JuliarCompiler();
                compiler.isDebug = true;
                InputStream inputStream = new ByteArrayInputStream(theCode.getBytes("UTF-8"));
                ServletOutputStream outputStream = response.getOutputStream();
                System.setOut( new PrintStream( response.getOutputStream() ) );
                List<String> errors = compiler.compile(inputStream, ".", false);

                if (errors != null || errors.size() > 0){
                    for(int i = 0; i < errors.size(); i++){
                        outputStream.println( errors.get( i ));
                    }
                }
                outputStream.flush();
            }
            catch (Exception ex){
                response.getOutputStream().println();
                response.getOutputStream().println( ex.getMessage() );
            }

        }
    }
}
