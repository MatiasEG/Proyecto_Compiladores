package minijava.compiler.exception.semanticP1.method;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionMethodMainDoesNotExist extends SemanticException {

    public SemanticExceptionMethodMainDoesNotExist(){
        super("No existe ninguna clase con metodo main.", new Token("idMetVar", "main", 0));
    }
}
