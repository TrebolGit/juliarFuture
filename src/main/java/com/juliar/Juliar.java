package com.juliar;

import com.bugsnag.Bugsnag;
import com.juliar.errors.ErrorListener;
import com.juliar.errors.JuliarLogger;
import com.juliar.interpreter.Interpreter;
import com.juliar.parser.JuliarLexer;
import com.juliar.parser.JuliarParser;
import com.juliar.symboltable.SymbolTable;
import com.juliar.vistor.Visitor;
import com.juliar.web.SimpleHTTPServer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Juliar {
	public static boolean isDebug = false;
	public static boolean isRepl = false;
	public static boolean isInline = false;
	public static int port = 48042;

	private ErrorListener errors;
	private String inputFileName;
	private Visitor visitor;

	public static void main(String[] args) throws URISyntaxException, IOException {
		Bugsnag bugsnag = new Bugsnag("c7e03c1e69143ad2fb1f3ea13ed8fda0");
		bugsnag.addCallback(report ->report.addToTab("subsystem", "name", "2018"));

		bugsnag.notify(new RuntimeException("Initiated"));


		try {
			String[] unparsedArgs = parseFlags(args);

			if(isInline){
				String unparsedStr = String.join(" ", unparsedArgs);
				Juliar compiler = new Juliar();
				InputStream stream = new ByteArrayInputStream(unparsedStr.getBytes(StandardCharsets.UTF_8));
				compiler.compile(stream, "", false);
				return;
			}
			else if(isRepl) {
				String unparsedStr = String.join(" ", unparsedArgs);
				Juliar compiler = new Juliar();
				Scanner reader = new Scanner(System.in);
				System.out.print("Welcome to Juliar REPL. Please type in the command and press enter to execute\n>");
				while (true) {
					String s = reader.nextLine();
					s = "function main() = {" + s + "}";
					InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
					compiler.compile(stream, "", false);
					System.out.print("\n>");
					return;
				}
			}
			else if(unparsedArgs.length == 0) {
				SimpleHTTPServer.main();
				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().browse(new URI("http://127.0.0.1:" + Integer.toString(port)));
				return;
			}
			else if (startupInstructions(unparsedArgs)) {
				return;
			}

			Boolean compileFlag = false;
			String fileName = unparsedArgs[0];
			String outputPath = "/";
			if(unparsedArgs.length > 1){
				outputPath = unparsedArgs[1];
				compileFlag = true;
				JuliarLogger.log("Compiling...");
			}


			Juliar compiler = new Juliar();
			compiler.compile(fileName, outputPath, compileFlag);

		} catch (Exception ex) {
			JuliarLogger.log("Errored out at: " + ex.getMessage());
		}
	}

	private static boolean startupInstructions(String[] args) {
		JuliarLogger.log("Juliar Compiler - Copyright (C) 2018");

		if (args.length != 1 && args.length != 2) {
			JuliarLogger.log("Usage: java -jar Juliar.jar <source file> <output path> <optional: -repl, -inline, -debug>");
			JuliarLogger.log("Path to Juliar source file");
			JuliarLogger.log("Path to output directory if compiled.");
			JuliarLogger.log("If output path is undefined, source file will be interpreted");
			JuliarLogger.log("If you would like to run a server, add -server flag");
			return true;
		}
		return false;
	}

	private static String[] parseFlags(String[] args) {
		ArrayList<String> unparsed = new ArrayList<>();
		boolean flag_port = false;
		for(String arg: args) {
			if(arg.startsWith("-")) switch (arg) {
				case "-debug":
					isDebug = true;
					JuliarLogger.log("debug is on");
					break;
				case "-repl":
					isRepl = true;
					break;
				case "-inline":
					isInline = true;
					break;
				case "-port":
					flag_port = true;
					break;
				default:
					break;
			}
			else if(flag_port){
				flag_port = false;
				port = Integer.parseUnsignedInt(arg);
			}
			else{
				unparsed.add(arg);
			}
		}
		return unparsed.toArray(new String[0]);
	}

	public List<String> compile(String source, String outputPath, boolean compilerFlag) {
		try {
			inputFileName = source;
			FileInputStream fileInputStream = new FileInputStream(source);
			return compile(fileInputStream, outputPath, compilerFlag);
		}
		catch (Exception ex) {
			JuliarLogger.log(ex.getMessage());
		}

		return new ArrayList<>();
	}

	public List<String> compile(InputStream b, String outputfile, boolean compilerFlag) {
		try {
			SymbolTable.clearSymbolTable();
			JuliarParser parser = parse( b );

			errors = new ErrorListener();
			parser.addErrorListener(errors);

			// call parse statement.
			// This will parse a single line to validate the syntax
			if (isRepl) {
				executeCommandLineRepl(parser);
			}
			else {
				if (executeCompiler(outputfile, compilerFlag, parser)) {
					return errors.errorList();
				}

			}
		} catch (Exception ex) {
			JuliarLogger.log(ex.getMessage());
		}

		return new ArrayList<>();
	}


	public boolean queryFunction( String funcName ){
		if (visitor != null) {
			return visitor.queryFunction(funcName);
		}

		return false;
	}

	/*
    Will execute the compiler or the interpreter.
     */
	private boolean executeCompiler(String outputfile, boolean compilerFlag, JuliarParser parser) throws IOException {
		// Calls the parse CompileUnit method
		// to parse a complete program
		// then calls the code generator.

		JuliarParser.CompileUnitContext context = parser.compileUnit();
		if (isDebug) {
			JuliarLogger.log(context.toStringTree(parser));
		}

		visitor = new Visitor((imports, linesToSkip) -> {
		}, true);
		visitor.visit(context);

		if (!errors.errorList().isEmpty() || !visitor.getErrorList().isEmpty()) {
			for (String error : errors.errorList()) {
				JuliarLogger.logerr(error);
			}

			for (String error : visitor.getErrorList()) {
				JuliarLogger.logerr(error);
			}

			return true;
		}
		if (compilerFlag) {
			com.juliar.codegenerator.CodeGenerator generator = new com.juliar.codegenerator.CodeGenerator(isDebug);
			generator.generate(visitor.instructions(), outputfile);
		}
      /*
      ReadWriteBinaryFile readWriteBinaryFile = new ReadWriteBinaryFile();
      readWriteBinaryFile.write(inputFileName, visitor.instructions());

      InstructionInvocation invocation = readWriteBinaryFile.read( inputFileName );
      if (invocation != null) {
         new Interpreter( invocation );
      }*/

		new Interpreter(visitor.instructions());


		return false;
	}

	/*
    Runs the REPL engine from the command line.
    TODO: Does this work?
     */
	private void executeCommandLineRepl(JuliarParser parser) {
		JuliarParser.CompileUnitContext context = parser.compileUnit();
		if (isDebug) {
			JuliarLogger.log(context.toStringTree(parser));
		}
        Visitor v = new Visitor(new ImportsInterface() {
            @Override
            public void createTempCallback(String imports, int linesToSkip) {

            }
        }, true);

        v.visit(context);
        Interpreter i = new Interpreter(v.instructions());
	}


	private JuliarParser parse(InputStream b) throws IOException {
		JuliarParser parser;
		CharStream s = CharStreams.fromStream(b);

		JuliarLexer lexer = new JuliarLexer(s);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		parser = new JuliarParser(tokenStream);

		parser.removeErrorListeners();

		return parser;
	}
}
