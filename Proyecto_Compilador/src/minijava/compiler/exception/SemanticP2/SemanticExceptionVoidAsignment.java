package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionVoidAsignment extends SemanticException {

    public SemanticExceptionVoidAsignment(Token varTk){
        super("Se intenta crear una nueva variable local con un tipo void.", varTk);
    }
}
