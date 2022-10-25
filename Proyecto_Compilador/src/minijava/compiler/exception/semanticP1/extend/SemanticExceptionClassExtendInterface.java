package minijava.compiler.exception.semanticP1.extend;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionClassExtendInterface extends SemanticException {

    public SemanticExceptionClassExtendInterface(Token extendedInterface){
        super("Una clase concreta esta intentado extender de una interfaz.", extendedInterface);
    }
}
