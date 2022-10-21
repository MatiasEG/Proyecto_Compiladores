package minijava.compiler.exception.semanticP1.classinterface;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionClassRefNotExist extends SemanticException {

    public SemanticExceptionClassRefNotExist(Token refType){
        super("El tipo referencia \'"+refType.getLexeme()+"\' no esta definido en el codigo.", refType);
    }
}
