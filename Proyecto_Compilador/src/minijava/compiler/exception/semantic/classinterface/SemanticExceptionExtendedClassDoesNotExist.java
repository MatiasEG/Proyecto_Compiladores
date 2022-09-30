package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClaseInterface;

public class SemanticExceptionExtendedClassDoesNotExist extends SemanticException {

    private ClaseInterface claseInterface;

    public SemanticExceptionExtendedClassDoesNotExist(ClaseInterface claseInterface, Token errorClass){
        super("La clase \'"+errorClass.getLexeme()+"\' de la que busca heredar la clase \'"+claseInterface.getNombre()+"\' no esta definida en el codigo.", errorClass);
        this.claseInterface = claseInterface;
    }
}
