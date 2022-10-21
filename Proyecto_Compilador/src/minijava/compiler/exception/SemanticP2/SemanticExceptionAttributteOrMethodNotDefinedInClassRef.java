package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionAttributteOrMethodNotDefinedInClassRef extends SemanticException {

    // TODO esta bien tener una excepcion para ambos?
    public SemanticExceptionAttributteOrMethodNotDefinedInClassRef(Token idVarNotDefined){
        super("El atributo/metodo que se quiso invocar de otra clase, no esta definido en la clase.", idVarNotDefined);
    }
}
