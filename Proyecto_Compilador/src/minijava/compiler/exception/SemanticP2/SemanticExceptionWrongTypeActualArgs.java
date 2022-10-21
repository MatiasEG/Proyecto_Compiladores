package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionWrongTypeActualArgs extends SemanticException {

    public SemanticExceptionWrongTypeActualArgs(Token wrongTypeToken){
        super("El tipo del argumento actual, no coincide con el tipo del argumento formal.", wrongTypeToken);
    }
}
