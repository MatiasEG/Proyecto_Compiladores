package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClaseInterface;

public class SemanticExceptionImplementedClassDoesNotExist extends SemanticException {

    private ClaseInterface claseInterface;
    private Token errorClass;

    public SemanticExceptionImplementedClassDoesNotExist(ClaseInterface claseInterface, Token errorClass){
        super("La clase \'"+errorClass+"\' que \'"+claseInterface.getNombre()+"\' busca implementar no esta definida en el codigo.", errorClass);
        this.claseInterface = claseInterface;
        this.errorClass = errorClass;
    }
}
