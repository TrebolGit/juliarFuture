export CLASSPATH="$CLASSPATH:jars/antlr-4.6-complete.jar"
java org.antlr.v4.Tool src/com/juliar/parser/juliar.g4 -o . -no-listener -package com.juliar.parser -visitor

