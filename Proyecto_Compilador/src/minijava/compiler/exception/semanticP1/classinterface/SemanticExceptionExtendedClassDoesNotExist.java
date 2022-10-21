package minijava.compiler.exception.semanticP1.classinterface;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionExtendedClassDoesNotExist extends SemanticException {

    private ClassOrInterface classOrInterface;

    public SemanticExceptionExtendedClassDoesNotExist(ClassOrInterface classOrInterface, Token errorClass){
        super("La clase \'"+errorClass.getLexeme()+"\' de la que busca heredar la clase \'"+ classOrInterface.getNombre()+"\' no esta definida en el codigo.", errorClass);
        this.classOrInterface = classOrInterface;
    }
}
