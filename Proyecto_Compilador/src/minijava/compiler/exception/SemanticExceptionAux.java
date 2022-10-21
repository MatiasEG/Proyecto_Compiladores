package minijava.compiler.exception;

import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionAux extends SemanticException{

    public SemanticExceptionAux(String s){
        super(s, new Token("Error a verificar", "Error a verificar", -1));
    }
}
