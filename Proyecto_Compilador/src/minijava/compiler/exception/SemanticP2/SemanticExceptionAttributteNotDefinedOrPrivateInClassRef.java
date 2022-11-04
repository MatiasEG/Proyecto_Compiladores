package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionAttributteNotDefinedOrPrivateInClassRef extends SemanticException {

    public SemanticExceptionAttributteNotDefinedOrPrivateInClassRef(Token idVarNotDefined){
        super("El atributo que se quiso invocar de otra clase, no esta definido en la clase o bien es privado y por lo tanto no es visible.", idVarNotDefined);
    }
}
