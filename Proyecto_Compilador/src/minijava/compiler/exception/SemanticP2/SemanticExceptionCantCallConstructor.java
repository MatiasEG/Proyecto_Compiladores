package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionCantCallConstructor extends SemanticException {

    public SemanticExceptionCantCallConstructor(Token idMet){
        super("No es posible invocar el constructor deseado.", idMet);
    }
}
