package minijava.compiler.exception.semanticP1.classinterface;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionClassImplementClass extends SemanticException {


    public SemanticExceptionClassImplementClass(Token idClaseImpl){
        super("Una clase esta intentando implementar una clase.", idClaseImpl);
    }
}
