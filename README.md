# juliar.Future
[![jfanimated](https://cloud.githubusercontent.com/assets/11934545/22674649/0b2d5d68-ecaf-11e6-9141-40ac1aefaa16.gif)](https://www.juliar.org/)

[![version](https://img.shields.io/badge/version-0.0.1-green.svg)](https://juliar.org/downloads.php)
[![Build Status](https://travis-ci.org/juliarLang/juliarFuture.svg?branch=master)](https://travis-ci.org/juliarLang/juliarFuture)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9a508bdddb8747bf9ed8e39bddfb10f2)](https://www.codacy.com/app/TheAndreiM/juliarFuture?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=juliarLang/juliarFuture&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/4bdc346c-d5b6-41df-b491-9361caba6094)](https://codebeat.co/projects/github-com-juliarlang-juliarfuture-master)

Juliar.Future is a programming language that is compiled directly into Java bytecode and can run in any JRE.
The aim of Juliar is to provide an easy to use compact system to create programs fast and run them on any system.
No installation or configuration needed.

### IDE comes built-in!

![JuliarFuture](https://user-images.githubusercontent.com/11934545/29492056-748cb48a-853d-11e7-94b2-159a32582bc5.png)

*If you are using Linux, we suggest that you use Oracle Java instead of OpenJava.

For more information, please check out our official website at [https://juliar.org](http://juliar.org)

## Contribute

Check out [CONTRIBUTING.md](https://github.com/juliarLang/juliarFuture/blob/master/CONTRIBUTING.md)
to find out how you can contribute to the project.


## Intro
Juliar.Future is the latest iteration of Juliar Programming Language. The compiler is built on top of Java, so wherever Java can be run, Juliar can run.

## Download
The latest stable release can be found at:

https://juliar.org/downloads.php

## Install

Download Juliar from 
https://juliar.org/downloads.php

After downloading the JAR file, you have 2 ways to launch it.
If you are a beginner or preffer a Graphical Window, then you can just double click juliarCompiler.jar

If you prefer a console/terminal, then open juliarCompiler.jar folder in terminal/console. 

## Modes
JuliarCompiler.jar works in 3 modes. It can be compiled, interpreted, ran as FastCGI web service.

## Editor Mode
Instead of using terminal, you can run a built-in editor by double clicking on `JuliarFuture.jar` file.
 
This provides a convenient and easy way to edit, build, interpret, and compile Juliar files. 
This method is recommended for people just starting with the programming language.

## Compile
JuliarCompiler.jar works on all platforms that have Java installed. So it is system independent!
run it in CommandPrompt/Console using the following command:

`
java -jar JuliarCompiler.jar something.jrl outputJARname
`

Where something.jrl is Juliar script and output is output name.
Because Juliar compiles directly into Java bytecode, Juliar programs can run anywhere Java is installed.

## Interpret
JuliarCompiler.jar can also interpret code for those people that want to tweak and tinker the code on the go.
You can have Juliar interpret by leaving out the output argument

`
java -jar JuliarCompiler.jar something.jrl
`
This will interpret the code and output the results on the screen. You can pipe the results to a file by using '>' followed by file name.

## FastCGI
JuliarCompiler.jar can also run as web service. You can connect JuliarCompiler.jar
to Apache, NGINX, Lighttpd, and many other services to process HTML files and output results to the user. 
JuliarCompiler.jar
makes it very easy to have a web server running by providing configurations for most popular web servers.
To launch JuliarCompiler.jar in FastCGI mod:

`
java -jar -DFCGI_PORT=9000 JuliarCompiler.jar
`


## Why?
You might be wondering, why should you use Juliar instead of any other programming languages.
The reason is portability, ease of use, and write once and run anywhere.

Juliar.Future doesn't require you to install anything, so you can take it anywhere on your usb drive.

Juliar.Future is simple to write, no need to know confusing syntax.

Juliar.Future runs in Java. Anywhere Java can run, Juliar can run.

## Creating Juliar File
Create a text file called "something.jrl". jrl is the extension juliar uses for its files! You can name it anything you want as long as it has an extension of .jrl.
First you need a main function,
`
function main() = {
}
`
The main function is the entry point of the code. If you do not have a main function, that means the code cannot be ran directly. The point of having the main function
is so that when code run directly it will perform some action. You might not need to perform that action if the code is included in.
Please keep in mind that if you have a main function in an included script, it won't run unless you explicitly call the main function in that included script.

## Creating and calling a custom function

Creating a custom function is very simple:

`function foo() = {
}
`

Where foo is function name. You might be wondering why is there an equal sign? The reason is that '=' sign copies the content of {} into a callable function.
Juliar is also partly a functional language and if you do not include '='. Juliar would think that you are getting back an object from foo() call and applying {}
on it. So potentially you can have something like function foo(x){3,2} = { return x+$} which will return x+3,x+2 as an object array.
Calling a function is very simple:

`
functionToCall(x,y);
`

Where x and y are variables. You can call custom functions the same way.

## Hoisting

Juliar uses function hoisting. Unlike C/C++, functions can be declared in any order (even if they are declared after a functional call).

## Function Overloading

Juliar currently doesn't support overloading. However, this may change in the future. Let us know how you feel!

## Variable Declaration

Variables are declared using one of the following declarations: `int`, `double`, `float`, `string`, `obj`. Please note, if you are not sure what type it is going to be, you can let the compiler
decide by using `var` instead.

## Printing to Screen
There are two function calls you should know in order to print in Juliar.
They are `printLine` and `print`. The difference is that `printLine` adds
a new line character, while `print` does not. Common uses `print(64);`, `print(2.3);`, `printLine("Brighter Future");`.

## Creating Classes
With Juliar, you don't need to use "new" to create an instance of a class.
```
class node = {
    int data;
}

function main() = {
    node one;
    one::data;
}
```

## Writing/Opening Files

Juliar uses "Open Format" standard for opening and storing files. Hence, it should potentially work on all Operating Systems. To Open a file just do
`
fileOpen("filename");
`

## Prefix Notation

Juliar allows prefix notation. That means you can arithmetic functions such as +,-,/,* and % before each operation and it will apply to everything after it.
For example, you can do:

`
int x = + 3 2 5 10;
`

which is the equivalent of doing

`
int x = 3 + 2 + 5 + 10;
`


## Installing from Source


Download the latest version from https://www.github.com/juliarLang/juliarFuture

or Clone in Desktop: 

Once downloaded and unzipped, deppending on your system
double click on DoEverything.bat if on Windows
or DoEverything.sh if on Linux, Mac, or UNIX.

If you are compiling on Windows, make sure your classpath
is set correctly.