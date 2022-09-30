package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionMethodMainDoesNotExist extends SemanticException {

    public SemanticExceptionMethodMainDoesNotExist(){
        super("No existe ninguna clase con metodo main.", new Token("idMetVar", "main", 0));
    }
}
