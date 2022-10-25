package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionCantCallConstructorOfInterface extends SemanticException {

    public SemanticExceptionCantCallConstructorOfInterface(Token idMet){
        super("No es posible invocar constructores de interfaces.", idMet);
    }
}
