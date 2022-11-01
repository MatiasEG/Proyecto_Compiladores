package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionMethodNotDefinedInClassRef extends SemanticException {

    public SemanticExceptionMethodNotDefinedInClassRef(Token idVarNotDefined){
        super("El metodo que se quiso invocar de otra clase, no esta definido en la clase.", idVarNotDefined);
    }
}
