package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.ClaseInterface;

public class SemanticExceptionImplementedClassDoesNotExist extends SemanticException{

    private ClaseInterface claseInterface;

    public SemanticExceptionImplementedClassDoesNotExist(ClaseInterface claseInterface, String errorClass){
        super("La clase \'"+errorClass+"\' de la que busca ser implementada por \'"+claseInterface.getNombre()+"\' no esta definida en el codigo.");
        this.claseInterface = claseInterface;
    }

    public String getLexeme(){
        return claseInterface.getNombre();
    }

    public int getRow(){
        return claseInterface.getLineaDeclaracion();
    }
}
