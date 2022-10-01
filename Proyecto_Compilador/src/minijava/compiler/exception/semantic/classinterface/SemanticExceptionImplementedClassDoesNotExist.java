package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionImplementedClassDoesNotExist extends SemanticException {

    private ClassOrInterface classOrInterface;
    private Token errorClass;

    public SemanticExceptionImplementedClassDoesNotExist(ClassOrInterface classOrInterface, Token errorClass){
        super("La clase \'"+errorClass+"\' que \'"+ classOrInterface.getNombre()+"\' busca implementar no esta definida en el codigo.", errorClass);
        this.classOrInterface = classOrInterface;
        this.errorClass = errorClass;
    }
}
