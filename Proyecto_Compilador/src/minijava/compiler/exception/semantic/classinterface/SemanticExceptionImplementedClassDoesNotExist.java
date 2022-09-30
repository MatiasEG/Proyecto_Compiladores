package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClaseInterface;

public class SemanticExceptionImplementedClassDoesNotExist extends SemanticException {

    private ClaseInterface claseInterface;

    public SemanticExceptionImplementedClassDoesNotExist(ClaseInterface claseInterface, String errorClass){
        super("La clase \'"+errorClass+"\' de la que busca ser implementada por \'"+claseInterface.getNombre()+"\' no esta definida en el codigo.", claseInterface.getClaseOrinterfaceToken());
        this.claseInterface = claseInterface;
    }
}
