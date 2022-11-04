package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionEmptyReturn extends SemanticException {

    public SemanticExceptionEmptyReturn(Token returnTk){
        super("El return esta vacio y el metodo requiere algun tipo como retorno.", returnTk);
    }
}
