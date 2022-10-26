package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionWrongQuantityParameters extends SemanticException {

    public SemanticExceptionWrongQuantityParameters(Token methodName){
        super("La cantidad de parametros con la que se quiso llamar al metodo \'"+methodName.getLexeme()+"\' es incorrecta.", methodName);
    }
}
