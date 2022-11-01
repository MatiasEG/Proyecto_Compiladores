package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionAttributteNotDefinedInClassRef extends SemanticException {

    public SemanticExceptionAttributteNotDefinedInClassRef(Token idVarNotDefined){
        super("El atributo que se quiso invocar de otra clase, no esta definido en la clase.", idVarNotDefined);
    }
}
