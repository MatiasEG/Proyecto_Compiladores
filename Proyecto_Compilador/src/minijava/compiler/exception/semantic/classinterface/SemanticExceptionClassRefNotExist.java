package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionClassRefNotExist extends SemanticException {

    public SemanticExceptionClassRefNotExist(Token refType){
        super("El tipo referencia \'"+refType.getLexeme()+"\' no esta definido en el codigo.", refType);
    }
}
